package com.example.consultationclinicapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteOpenHelper(
    context: Context,
): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "example.db"
        // 資料庫版本，當資料筆資料增加需要調整版本號，目前為版本=2
        private const val DATABASE_VERSION = 2

        // 建立 BodyParts(身體部位) 表的 SQL 語句
        private const val CREATE_TABLE_BODYPARTS = """
            CREATE TABLE IF NOT EXISTS BodyParts (
                BodyPartID INTEGER PRIMARY KEY,
                Gender INTEGER,
                PartName TEXT,
                En_PartName TEXT,
                Side INTEGER
            )
        """

        // 建立 DETAILPARTS(部位細節) 表的 SQL 語句
        private const val CREATE_TABLE_DETAILPARTS = """
            CREATE TABLE IF NOT EXISTS DetailParts (
                detail_part_id INTEGER PRIMARY KEY,
                detail_part_name TEXT,
                part_id INTEGER,
                FOREIGN KEY(part_id) REFERENCES BodyParts(part_id)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_BODYPARTS)
        db.execSQL(CREATE_TABLE_DETAILPARTS)
        // 插入預設資料
        insertDefaultData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion<2){
            db.execSQL("DROP TABLE IF EXISTS BodyParts")
            db.execSQL("DROP TABLE IF EXISTS DetailParts")
            onCreate(db)
        }
    }

    private fun insertDefaultData(db: SQLiteDatabase) {
        // TABLE_BODYPARTS
        val defaultData1 = listOf(
            BodyPart(1, 0, "頭部", "Head", 0),
            BodyPart(2, 0, "頸部", "Neck", 0),
            BodyPart(3, 0, "胸部", "Chest", 0),
            BodyPart(4, 0, "腹部", "Abdomen", 0),
            BodyPart(5, 0, "下腹部", "Lower Abdomen", 0),
            BodyPart(6, 0, "全身", "Whole Body", 0),
            BodyPart(7, 0, "皮膚", "Skin", 0),
            BodyPart(8, 0, "心理", "Psychological", 0),
            BodyPart(9, 0, "手部", "Hands", 0),
            BodyPart(10, 0, "腿部", "Legs", 0),
            BodyPart(11, 0, "足部", "Feet", 0),
            BodyPart(12, 0, "背部", "Back", 1),
            BodyPart(13, 0, "腰部", "Waist", 1),
            BodyPart(14, 0, "臀部", "Buttocks", 1),
            BodyPart(15, 0, "頭部", "Head", 1),
            BodyPart(16, 0, "頸部", "Neck", 1),
            BodyPart(17, 0, "全身", "Whole Body", 1),
            BodyPart(18, 0, "皮膚", "Skin", 1),
            BodyPart(19, 0, "心理", "Psychological", 1),
            BodyPart(20, 0, "手部", "Hands", 1),
            BodyPart(21, 0, "腿部", "Legs", 1),
            BodyPart(22, 0, "足部", "Feet", 1),
            BodyPart(23, 1, "頭部", "Head", 0),
            BodyPart(24, 1, "頸部", "Neck", 0),
            BodyPart(25, 1, "胸部", "Chest", 0),
            BodyPart(26, 1, "腹部", "Abdomen", 0),
            BodyPart(27, 1, "下腹部", "Lower Abdomen", 0),
            BodyPart(28, 1, "全身", "Whole Body", 0),
            BodyPart(29, 1, "皮膚", "Skin", 0),
            BodyPart(30, 1, "心理", "Psychological", 0),
            BodyPart(31, 1, "手部", "Hands", 0),
            BodyPart(32, 1, "腿部", "Legs", 0),
            BodyPart(33, 1, "足部", "Feet", 0),
            BodyPart(34, 1, "背部", "Back", 1),
            BodyPart(35, 1, "腰部", "Waist", 1),
            BodyPart(36, 1, "臀部", "Buttocks", 1),
            BodyPart(37, 1, "頭部", "Head", 1),
            BodyPart(38, 1, "頸部", "Neck", 1),
            BodyPart(39, 1, "全身", "Whole Body", 1),
            BodyPart(40, 1, "皮膚", "Skin", 1),
            BodyPart(41, 1, "心理", "Psychological", 1),
            BodyPart(42, 1, "手部", "Hands", 1),
            BodyPart(43, 1, "腿部", "Legs", 1),
            BodyPart(44, 1, "足部", "Feet", 1)
        )

        val sql1 = "INSERT INTO BodyParts (BodyPartID, Gender, PartName, En_PartName, Side) VALUES (?, ?, ?, ?, ?)"
        val stmt1 = db.compileStatement(sql1)
        for (part in defaultData1) {
            stmt1.bindLong(1, part.BodyPartID.toLong())
            stmt1.bindLong(2, part.Gender.toLong())
            stmt1.bindString(3, part.PartName)
            stmt1.bindString(4, part.En_PartName)
            stmt1.bindLong(5, part.Side.toLong())
            stmt1.executeInsert()
            stmt1.clearBindings()
        }

        // TABLE_DETAILPARTS
        val defaultData2 = listOf(
            DetailPart(1, "頭", 1),
            DetailPart(2, "臉", 1),
            DetailPart(3, "鼻", 1),
            DetailPart(4, "耳", 1),
            DetailPart(5, "眼", 1),
            DetailPart(6, "喉", 1),
            DetailPart(7, "口", 1),
            DetailPart(8, "頸部", 2),
            DetailPart(9, "胸腔", 3),
            DetailPart(10, "肺", 3),
            DetailPart(11, "氣管", 3),
            DetailPart(12, "心臟", 3),
            DetailPart(13, "乳房", 3)
            // 這裡可以繼續添加更多細節部位
        )

        val sql2 = "INSERT INTO DetailParts (detail_part_id, detail_part_name, part_id) VALUES (?, ?, ?)"
        val stmt2 = db.compileStatement(sql2)
        for (detailPart in defaultData2) {
            stmt2.bindLong(1, detailPart.detailPartId.toLong())
            stmt2.bindString(2, detailPart.detailPartName)
            stmt2.bindLong(3, detailPart.partId.toLong())
            stmt2.executeInsert()
            stmt2.clearBindings()
        }
    }

    // 用兩個條件bodyType和frontBack查詢BodyParts的資料表結果
    fun getBodyPartsByTypeAndPosition(bodyType: Int, frontBack: Int): List<BodyPart> {
        val db = this.readableDatabase
        val selection = "body_type = ? AND front_back = ?"
        val selectionArgs = arrayOf(bodyType.toString(), frontBack.toString())
        val cursor = db.query(
            "BodyParts",      // 表名
            null,             // 欄位名（null 表示選擇所有欄位）
            selection,        // 選擇條件
            selectionArgs,    // 選擇條件的參數
            null,             // groupBy
            null,             // having
            null              // orderBy
        )

        val parts= mutableListOf<BodyPart>()
        with(cursor) {
            while (moveToNext()) {
                val partId = getInt(getColumnIndexOrThrow("part_id"))
                val partName = getString(getColumnIndexOrThrow("part_name"))
                val en_partName = getString(getColumnIndexOrThrow("en_part_name"))
                parts.add(BodyPart(partId, bodyType, partName, en_partName, frontBack))
            }
            close()
        }
        db.close()
        return parts
    }

    // 用兩個條件partName和frontBack查詢BodyParts的資料表結果
    fun getBodyTypeByPartNameAndPosition(partName: String, frontBack: Int): Int? {
        val db = this.readableDatabase
        // 更新選擇條件以包括 part_name 或 en_part_name
        val selection = "(part_name = ? OR en_part_name = ?) AND front_back = ?"
        val selectionArgs = arrayOf(partName, partName, frontBack.toString())
        val cursor = db.query(
            "BodyParts",
            arrayOf("part_id"),  // 只需查詢 part_id 欄位
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var partid: Int? = null
        if (cursor.moveToFirst()) {  // 如果查詢到數據，則讀取第一條記錄的 part_id
            partid = cursor.getInt(cursor.getColumnIndexOrThrow("part_id"))
        }
        cursor.close()
        db.close()
        return partid
    }


    // 用條件partID查詢DetailParts的資料表結果
    fun getDetailPartsByPartId(partId: Int): List<DetailPart> {
        val db = this.readableDatabase
        val cursor = db.query(
            "DetailParts",
            arrayOf("detail_part_id", "detail_part_name", "part_id"),
            "part_id = ?",
            arrayOf(partId.toString()),
            null, null, null
        )

        val detailParts = mutableListOf<DetailPart>()
        with(cursor) {
            while (moveToNext()) {
                val detailPartId = getInt(getColumnIndexOrThrow("detail_part_id"))
                val detailPartName = getString(getColumnIndexOrThrow("detail_part_name"))
                detailParts.add(DetailPart(detailPartId, detailPartName, partId))
            }
            close()
        }
        db.close()
        return detailParts
    }
}

data class BodyPart(
    val BodyPartID: Int,
    val Gender: Int,
    val PartName: String,
    val En_PartName: String,
    val Side: Int
)

data class DetailPart(
    val detailPartId: Int,
    val detailPartName: String,
    val partId: Int
)
