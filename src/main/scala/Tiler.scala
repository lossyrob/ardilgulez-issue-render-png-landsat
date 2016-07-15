package tutorial

import geotrellis.vector.Extent
import geotrellis.proj4.CRS
import geotrellis.raster._
import geotrellis.raster.io.geotiff._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by root on 14.07.2016.
  */

object Tiler {
	val defaultPath = "data/output/output.tif"

	def apply( bandIds : String* ): Tiler = new Tiler( defaultPath, bandIds.toArray )

	def apply( bandIds : Array[String] ): Tiler = new Tiler( defaultPath, bandIds )

	def apply( bandIds : ArrayBuffer[String] ): Tiler = new Tiler( defaultPath, bandIds.toArray )

	def apply( bandIds : Traversable[String] ): Tiler = new Tiler( defaultPath, bandIds.toArray )

	def apply( outputPath : String, bandIds : String*): Tiler = new Tiler( outputPath, bandIds.toArray )

	def apply( outputPath : String, bandIds : Array[String] ): Tiler = new Tiler( outputPath, bandIds )

	def apply( outputPath : String, bandIds : ArrayBuffer[String] ): Tiler = new Tiler( outputPath, bandIds.toArray )

	def apply( outputPath : String, bandIds : Traversable[String] ): Tiler = new Tiler( outputPath, bandIds.toArray )

}

class Tiler ( outputPath : String, bandIds : Array[String] ){

	def bandPath(b: String) = s"data/landsat/LC81750342016185LGN00_B${b}.TIF"

	def obtainBands() : (ArrayMultibandTile, SinglebandGeoTiff) = {
		var tileArrayBuffer = ArrayBuffer[Tile]()
		var geoTiff : SinglebandGeoTiff = null
		bandIds.foreach((bandId) => {
			geoTiff = SinglebandGeoTiff(bandPath(bandId))
			tileArrayBuffer += geoTiff.tile
		})
		(ArrayMultibandTile(tileArrayBuffer), geoTiff)
	}

	def writeResultToFS(multiBand : MultibandTile, extent : Extent, crs : CRS) : Unit = {
		MultibandGeoTiff(multiBand, extent, crs).write(outputPath)
	}

	def parseTiles() : Unit = {
		val (multiBandTileArray, geotiff) = obtainBands()
		println("BURAYA KADAR TAMAM")
		writeResultToFS(multiBandTileArray, geotiff.extent, geotiff.crs)
	}

}