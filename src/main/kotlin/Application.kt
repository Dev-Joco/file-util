import com.jojo.fileutil.tree.FileTreeBuilder
import java.nio.file.Paths

fun main(vararg args: String) {
    val path = Paths.get("").toAbsolutePath().toString()

    FileTreeBuilder.build(path)
        .traverse()
}