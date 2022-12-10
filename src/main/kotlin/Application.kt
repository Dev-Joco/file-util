import com.jojo.fileutil.FileUtil
import com.jojo.fileutil.FileUtil2
import java.nio.file.Paths

fun main(vararg args: String) {
//    val path = "src/main/"
//    val path = Paths.get("").toAbsolutePath().toString()
//    val path = "C:\\Users\\JoJo\\IdeaProjects\\woowa-precourse\\study\\precourse-study-vending-machine\\src\\main\\kotlin\\vendingmachine"
//    val path = "C:\\Users\\JoJo\\AndroidStudioProjects\\Mwodeola\\app\\src\\main\\java"
//    val path = "C:\\Users\\JoJo\\AndroidStudioProjects\\Mwodeola\\app\\src\\main\\java\\com\\jojo\\android\\mwodeola"
//    val path = "C:\\Users\\JoJo\\AndroidStudioProjects\\Mwodeola"
//    val path = "C:\\Users\\JoJo\\AndroidStudioProjects\\Mwodeola\\app\\src"
//    val path = "C:\\Users\\JoJo\\AndroidStudioProjects\\Mwodeola\\app\\src\\main"
    val path = Paths.get("").toAbsolutePath().toString()
    FileUtil.printSubTree(path)
//    FileUtil2.printSubTree(path)
}