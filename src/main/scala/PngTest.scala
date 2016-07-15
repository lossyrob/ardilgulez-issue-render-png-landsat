package tutorial

import geotrellis.raster._
import geotrellis.raster.io.geotiff._

class PngTest() {

	val tiffPath = "data/output/output.tif"
	val bwPath = "data/output/bw.png"

	def makepng(): Unit = {
		println("Rendering PNG and saving to disk...")

		val conversionMap = {
			val multibandTile = MultibandGeoTiff(tiffPath).tile.convert(IntConstantNoDataCellType)

			multibandTile.combine(0, 1, 2) { (rBand, gBand, bBand) =>
				val r = if (isData(rBand)) { rBand } else 0
				val g = if (isData(gBand)) { gBand } else 0
				val b = if (isData(bBand)) { bBand } else 0

				if(r + g + b == 0) 0
				else {
					val color = ( r + g + b ) / 3
					((color & 0xFF) << 24) | ((color & 0xFF) << 16) | ((color & 0xFF) << 8) | 0xFF
				}
			}
		}

		conversionMap.renderPng().write(bwPath)
	}
}
