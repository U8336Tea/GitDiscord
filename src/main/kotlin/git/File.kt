package git

import java.util.*
import org.eclipse.egit.github.core.*
import org.eclipse.egit.github.core.client.GitHubClient
import org.eclipse.egit.github.core.service.CommitService
import org.eclipse.egit.github.core.service.ContentsService
import org.eclipse.egit.github.core.service.DataService
import org.eclipse.egit.github.core.service.RepositoryService

// Some help from https://gist.github.com/Detelca/2337731

/**
 * A file in a repo. Wrapper for RepositoryContents.
 *
 * @param client The client to use to access GitHub.
 * @param path The path to the file.
 * @param repo The repo that holds the file.
 */
class File(private val client: GitHubClient, val path: String, val repo: IRepositoryIdProvider) {
	private val dataService = DataService(client)
	private val repositoryContents = ContentsService().getContents(repo, path)[0]

	/**
	 * The contents of the file at [path].
	 */
	var contents: String get() {
		// Decoder doesn't like whitespace
		val content = repositoryContents.content.filterNot { it.isWhitespace() }
		val decoder = Base64.getDecoder()
		val decoded = decoder.decode(content)

		return decoded.map { it.toChar() }.joinToString("")
	} set(value) {
		updateContents(value)
	}

	/**
	 * Updates the contents of the file.
	 *
	 * @param contents The new contents of the file.
	 */
	// TODO: Should this be a setter? Both now to be sure.
	fun updateContents(contents: String) {
		val blob = Blob()
		blob.content = contents

		val sha = dataService.createBlob(repo, blob)

		val user = CommitUser()
		user.name = client.user
		user.date = Date()
		user.email = "something"

		val repositoryService = RepositoryService()
		val commitService = CommitService()
		val repository = repositoryService.getRepository(repo)
		val branch = repositoryService.getBranches(repo).first()
		val currentCommit = commitService.getCommit(repository, branch.commit.sha)
		val tree = dataService.getTree(repo, currentCommit.sha)

		val commit = Commit()
		commit.author = user
		commit.committer = user
		commit.message = "Bot commit"
		commit.sha = sha

		val treeEntry = TreeEntry()
		treeEntry.mode = TreeEntry.MODE_BLOB
		treeEntry.path = path
		treeEntry.sha = sha
		treeEntry.size = contents.length.toLong()
		treeEntry.type = TreeEntry.TYPE_BLOB

		val newTree = dataService.createTree(repo, arrayListOf(treeEntry), tree.sha)
		commit.tree = newTree

		dataService.createCommit(repo, commit)

		val resource = TypedResource()
		resource.type = TypedResource.TYPE_COMMIT
		resource.sha = commit.sha
		resource.url = commit.url

		val reference = dataService.getReference(repo, "heads/master")
		reference.`object` = resource
		dataService.editReference(repo, reference)
	}
}