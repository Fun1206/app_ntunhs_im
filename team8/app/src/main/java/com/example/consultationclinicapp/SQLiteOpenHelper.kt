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
        // 資料庫版本，當資料筆資料增加需要調整版本號，目前為版本=3
        private const val DATABASE_VERSION = 3

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

        // 建立 SubParts(部位細節) 表的 SQL 語句
        private const val CREATE_TABLE_SUBPARTS = """
            CREATE TABLE IF NOT EXISTS SubParts (
                SubPartID INTEGER PRIMARY KEY,
                SubPartName TEXT,
                En_SubPartName TEXT,
                BodyPartID INTEGER,
                FOREIGN KEY(BodyPartID) REFERENCES BodyParts(BodyPartID)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_BODYPARTS)
        db.execSQL(CREATE_TABLE_SUBPARTS)
        // 插入預設資料
        insertDefaultData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS BodyParts")
            db.execSQL("DROP TABLE IF EXISTS DetailParts")
            db.execSQL("DROP TABLE IF EXISTS SubParts")
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

        // TABLE_SUBPARTS
        val defaultData2 = listOf(
            SubPart(1, "頭", "Head", 1),
            SubPart(2, "臉", "Face", 1),
            SubPart(3, "鼻", "Nose", 1),
            SubPart(4, "耳", "Ear", 1),
            SubPart(5, "眼", "Eye", 1),
            SubPart(6, "喉", "Throat", 1),
            SubPart(7, "口", "Mouth", 1),
            SubPart(8, "頸部", "Neck", 2),
            SubPart(9, "胸腔", "Chest", 3),
            SubPart(10, "肺", "Lung", 3),
            SubPart(11, "氣管", "Trachea", 3),
            SubPart(12, "心臟", "Heart", 3),
            SubPart(13, "乳房", "Breast", 3),
            SubPart(14, "腹部", "Abdomen", 4),
            SubPart(15, "鼠蹊部", "Groin", 5),
            SubPart(16, "生殖系統", "Reproductive system", 5),
            SubPart(17, "泌尿系統", "Urinary system", 5),
            SubPart(18, "全身", "Whole body", 6),
            SubPart(19, "黏膜", "Mucous membrane", 6),
            SubPart(20, "骨骼", "Skeleton", 6),
            SubPart(21, "血管", "Blood vessels", 6),
            SubPart(22, "肌肉", "Muscles", 6),
            SubPart(23, "神經", "Nerves", 6),
            SubPart(24, "淋巴", "Lymphatic system", 6),
            SubPart(25, "內分泌(糖尿病、甲狀腺等)", "Endocrine (diabetes, thyroid, etc.)", 6),
            SubPart(26, "免疫(過敏、風濕、痛風等)", "Immune (allergies, rheumatism, gout, etc.)", 6),
            SubPart(27, "皮膚", "Skin", 7),
            SubPart(28, "心理", "Psychological", 8),
            SubPart(29, "手部", "Hands", 9),
            SubPart(30, "腿部", "Legs", 10),
            SubPart(31, "足部", "Feet", 11),
            SubPart(32, "背部", "Back", 12),
            SubPart(33, "腰部", "Waist", 13),
            SubPart(34, "髖部", "Hips", 14),
            SubPart(35, "臀部", "Buttocks", 14),
            SubPart(36, "肛門", "Anus", 14),
            SubPart(37, "頭", "Head", 15),
            SubPart(38, "臉", "Face", 15),
            SubPart(39, "鼻", "Nose", 15),
            SubPart(40, "耳", "Ear", 15),
            SubPart(41, "眼", "Eye", 15),
            SubPart(42, "喉", "Throat", 15),
            SubPart(43, "口", "Mouth", 15),
            SubPart(44, "頸部", "Neck", 16),
            SubPart(45, "全身", "Whole body", 17),
            SubPart(46, "黏膜", "Mucous membrane", 17),
            SubPart(47, "骨骼", "Skeleton", 17),
            SubPart(48, "血管", "Blood vessels", 17),
            SubPart(49, "肌肉", "Muscles", 17),
            SubPart(50, "神經", "Nerves", 17),
            SubPart(51, "淋巴", "Lymphatic system", 17),
            SubPart(52, "內分泌(糖尿病、甲狀腺等)", "Endocrine (diabetes, thyroid, etc.)", 17),
            SubPart(53, "免疫(過敏、風濕、痛風等)", "Immune (allergies, rheumatism, gout, etc.)", 17),
            SubPart(54, "皮膚", "Skin", 18),
            SubPart(55, "心理", "Psychological", 19),
            SubPart(56, "手部", "Hands", 20),
            SubPart(57, "腿部", "Legs", 21),
            SubPart(58, "足部", "Feet", 22),
            SubPart(59, "頭", "Head", 23),
            SubPart(60, "臉", "Face", 23),
            SubPart(61, "鼻", "Nose", 23),
            SubPart(62, "耳", "Ear", 23),
            SubPart(63, "眼", "Eye", 23),
            SubPart(64, "喉", "Throat", 23),
            SubPart(65, "口", "Mouth", 23),
            SubPart(66, "頸部", "Neck", 24),
            SubPart(67, "胸腔", "Chest", 25),
            SubPart(68, "肺", "Lung", 25),
            SubPart(69, "氣管", "Trachea", 25),
            SubPart(70, "心臟", "Heart", 25),
            SubPart(71, "乳房", "Breast", 25),
            SubPart(72, "腹部", "Abdomen", 26),
            SubPart(73, "鼠蹊部", "Groin", 27),
            SubPart(74, "生殖系統", "Reproductive system", 27),
            SubPart(75, "泌尿系統", "Urinary system", 27),
            SubPart(76, "全身", "Whole body", 28),
            SubPart(77, "黏膜", "Mucous membrane", 28),
            SubPart(78, "骨骼", "Skeleton", 28),
            SubPart(79, "血管", "Blood vessels", 28),
            SubPart(80, "肌肉", "Muscles", 28),
            SubPart(81, "神經", "Nerves", 28),
            SubPart(82, "淋巴", "Lymphatic system", 28),
            SubPart(83, "內分泌(糖尿病、甲狀腺等)", "Endocrine (diabetes, thyroid, etc.)", 28),
            SubPart(84, "免疫(過敏、風濕、痛風等)", "Immune (allergies, rheumatism, gout, etc.)", 28),
            SubPart(85, "皮膚", "Skin", 29),
            SubPart(86, "心理", "Psychological", 30),
            SubPart(87, "手部", "Hands", 31),
            SubPart(88, "腿部", "Legs", 32),
            SubPart(89, "足部", "Feet", 33),
            SubPart(90, "背部", "Back", 34),
            SubPart(91, "腰部", "Waist", 35),
            SubPart(92, "髖部", "Hips", 36),
            SubPart(93, "臀部", "Buttocks", 36),
            SubPart(94, "肛門", "Anus", 36),
            SubPart(95, "頭", "Head", 37),
            SubPart(96, "臉", "Face", 37),
            SubPart(97, "鼻", "Nose", 37),
            SubPart(98, "耳", "Ear", 37),
            SubPart(99, "眼", "Eye", 37),
            SubPart(100, "喉", "Throat", 37),
            SubPart(101, "口", "Mouth", 37),
            SubPart(102, "頸部", "Neck", 38),
            SubPart(103, "全身", "Whole body", 39),
            SubPart(104, "黏膜", "Mucous membrane", 39),
            SubPart(105, "骨骼", "Skeleton", 39),
            SubPart(106, "血管", "Blood vessels", 39),
            SubPart(107, "肌肉", "Muscles", 39),
            SubPart(108, "神經", "Nerves", 39),
            SubPart(109, "淋巴", "Lymphatic system", 39),
            SubPart(110, "內分泌(糖尿病、甲狀腺等)", "Endocrine (diabetes, thyroid, etc.)", 39),
            SubPart(111, "免疫(過敏、風濕、痛風等)", "Immune (allergies, rheumatism, gout, etc.)", 39),
            SubPart(112, "皮膚", "Skin", 40),
            SubPart(113, "心理", "Psychological", 41),
            SubPart(114, "手部", "Hands", 42),
            SubPart(115, "腿部", "Legs", 43),
            SubPart(116, "足部", "Feet", 44)
        )

        val sql2 = "INSERT INTO SubParts (SubPartID, SubPartName, En_SubPartName, BodyPartID) VALUES (?, ?, ?, ?)"
        val stmt2 = db.compileStatement(sql2)
        for (subPart in defaultData2) {
            stmt2.bindLong(1, subPart.SubPartID.toLong())
            stmt2.bindString(2, subPart.SubPartName)
            stmt2.bindString(3, subPart.En_SubPartName)
            stmt2.bindLong(4, subPart.BodyPartID.toLong())
            stmt2.executeInsert()
            stmt2.clearBindings()
        }
    }

    // 用兩個條件Gender和Side查詢BodyParts的資料表結果
    fun getBodyPartsByTypeAndPosition(Gender: Int, Side: Int): List<BodyPart> {
        val db = this.readableDatabase
        val selection = "Gender = ? AND Side = ?"
        val selectionArgs = arrayOf(Gender.toString(), Side.toString())
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
                val BodyPartID = getInt(getColumnIndexOrThrow("BodyPartID"))
                val PartName = getString(getColumnIndexOrThrow("PartName "))
                val En_PartName = getString(getColumnIndexOrThrow("En_PartName "))
                parts.add(BodyPart(BodyPartID, Gender, PartName, En_PartName, Side))
            }
            close()
        }
        db.close()
        return parts
    }

    // 用兩個條件PartName和Gender查詢BodyParts的資料表結果
    fun getBodyPartIDByPartNameAndGender(PartName: String, Gender: Int): Int? {
        val db = this.readableDatabase
        // 更新選擇條件以包括 PartName 和 Gender
        val selection = "PartName = ? AND Gender = ?"
        val selectionArgs = arrayOf(PartName, Gender.toString())
        val cursor = db.query(
            "BodyParts",
            arrayOf("BodyPartID"),  // 只需查詢 BodyPartID 欄位
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        var BodyPartID: Int? = null
        if (cursor.moveToFirst()) {  // 如果查詢到數據，則讀取第一條記錄的 BodyPartID
            BodyPartID = cursor.getInt(cursor.getColumnIndexOrThrow("BodyPartID"))
        }
        cursor.close()
        db.close()
        return BodyPartID
    }


    // 用條件BodyPartID查詢SubParts的資料表結果
    fun getSubPartsByPartId(BodyPartID: Int): List<SubPart> {
        val db = this.readableDatabase
        val cursor = db.query(
            "SubParts",
            null, // 欄位名（null 表示選擇所有欄位）
            "BodyPartID = ?",
            arrayOf(BodyPartID.toString()),
            null, null, null
        )

        val SubParts = mutableListOf<SubPart>()
        with(cursor) {
            while (moveToNext()) {
                val SubPartID = getInt(getColumnIndexOrThrow("SubPartID"))
                val SubPartName = getString(getColumnIndexOrThrow("SubPartName"))
                val En_SubPartName = getString(getColumnIndexOrThrow("En_SubPartName"))
                SubParts.add(SubPart(SubPartID, SubPartName, En_SubPartName, BodyPartID))
            }
            close()
        }
        db.close()
        return SubParts
    }
}

data class BodyPart(
    val BodyPartID: Int,
    val Gender: Int,
    val PartName: String,
    val En_PartName: String,
    val Side: Int
)

data class SubPart(
    val SubPartID: Int,
    val SubPartName: String,
    val En_SubPartName: String,
    val BodyPartID: Int
)