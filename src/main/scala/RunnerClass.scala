package tutorial

/**
  * Created by root on 15.07.2016.
  */
object RunnerClass {
	def main(args: Array[String]) {
		val inputArray = Array[String]("4", "6", "7")
		Tiler("data/output/output.tif", inputArray).parseTiles()
		val pngTest = new PngTest()
		println("PNG TEST")
		pngTest.makepng()
	}
}
