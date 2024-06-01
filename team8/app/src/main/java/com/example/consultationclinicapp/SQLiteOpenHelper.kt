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
        // 資料庫版本，當資料筆資料增加需要調整版本號，目前為版本=4
        private const val DATABASE_VERSION = 4

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

        // 建立 Symptoms(症狀) 表的 SQL 語句
        private const val CREATE_TABLE_SYMPTOMS = """
            CREATE TABLE IF NOT EXISTS Symptoms (
                SymptomID INTEGER PRIMARY KEY,
                SymName TEXT,
                En_SymName TEXT
            )
        """

        // 建立 SubPartSymptoms(細節部位與症狀關聯) 表的 SQL 語句
        private const val CREATE_TABLE_SUBPARTSYMPTOMS = """
            CREATE TABLE IF NOT EXISTS SubPartSymptoms (
                SubPartID INTEGER,
                SymptomID INTEGER,
                FOREIGN KEY(SubPartID) REFERENCES SubParts(SubPartID),
                FOREIGN KEY(SymptomID) REFERENCES Symptoms(SymptomID)
            )
        """

        // 建立 Departments(建議科別) 表的 SQL 語句
        private const val CREATE_TABLE_DEPARTMENTS = """
            CREATE TABLE IF NOT EXISTS Departments (
                DepartmentID INTEGER PRIMARY KEY,
                DpmName TEXT,
                En_DpmName TEXT,
                Description TEXT,
                En_Description TEXT
            )
        """
        // 建立 SymptomDepartments(症狀與科別關聯) 表的 SQL 語句
        private const val CREATE_TABLE_SYMPTOMDEPARTMENTS = """
            CREATE TABLE IF NOT EXISTS SymptomDepartments (
                SymptomID INTEGER,
                DepartmentID INTEGER,
                FOREIGN KEY(SymptomID) REFERENCES Symptoms(SymptomID),
                FOREIGN KEY(DepartmentID) REFERENCES Departments(DepartmentID)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_BODYPARTS)
        db.execSQL(CREATE_TABLE_SUBPARTS)
        db.execSQL(CREATE_TABLE_SYMPTOMS)
        db.execSQL(CREATE_TABLE_SUBPARTSYMPTOMS)
        db.execSQL(CREATE_TABLE_DEPARTMENTS)
        db.execSQL(CREATE_TABLE_SYMPTOMDEPARTMENTS)
        // 插入預設資料
        insertDefaultData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion < newVersion){
            db.execSQL("DROP TABLE IF EXISTS BodyParts")
            db.execSQL("DROP TABLE IF EXISTS DetailParts")
            db.execSQL("DROP TABLE IF EXISTS SubParts")
            db.execSQL("DROP TABLE IF EXISTS Symptoms")
            db.execSQL("DROP TABLE IF EXISTS SubPartSymptoms")
            db.execSQL("DROP TABLE IF EXISTS Departments")
            db.execSQL("DROP TABLE IF EXISTS SymptomDepartments")
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
        // TABLE_SYMPTOMS
        val defaultData3 = listOf(
            Symptom(1,"反胃噁心","Nausea"),
            Symptom(2,"頭暈","Dizziness"),
            Symptom(3,"意識不清","Confusion"),
            Symptom(4,"癲癇","Epilepsy"),
            Symptom(5,"頭皮血腫","Scalp hematoma"),
            Symptom(6,"皮膚發白","Pallor"),
            Symptom(7,"口渴","Thirst"),
            Symptom(8,"囊腫","Cyst"),
            Symptom(9,"視力異常","Visual abnormalities"),
            Symptom(10,"丘疹","Papules"),
            Symptom(11,"青春痘","Acne"),
            Symptom(12,"膿疱","Pustules"),
            Symptom(13,"囊腫","Cyst"),
            Symptom(14,"抬頭紋消失","Loss of forehead lines"),
            Symptom(15,"無法閉眼","Inability to close the eyes"),
            Symptom(16,"臉歪嘴斜","Facial droop"),
            Symptom(17,"面部麻痺","Facial paralysis"),
            Symptom(18,"面部刺痛","Facial tingling"),
            Symptom(19,"咀嚼肌肉異常","Abnormal chewing muscles"),
            Symptom(20,"流鼻水","Runny nose"),
            Symptom(21,"鼻塞","Nasal congestion"),
            Symptom(22,"鼻涕倒流","Postnasal drip"),
            Symptom(23,"打噴嚏","Sneezing"),
            Symptom(24,"流鼻血","Nosebleed"),
            Symptom(25,"打鼾","Snoring"),
            Symptom(26,"呼吸困難","Difficulty breathing"),
            Symptom(27,"呼吸急促","Shortness of breath"),
            Symptom(28,"呼吸聲異常","Abnormal breath sounds"),
            Symptom(29,"呼吸不順","Breathing difficulty"),
            Symptom(30,"耳鳴","Tinnitus"),
            Symptom(31,"聽力異常","Hearing loss"),
            Symptom(32,"耳道有分泌物","Ear discharge"),
            Symptom(33,"耳朵疼痛","Ear pain"),
            Symptom(34,"頭暈","Dizziness"),
            Symptom(35,"耳道阻塞","Ear canal obstruction"),
            Symptom(36,"頭痛","Headache"),
            Symptom(37,"耳朵腫痛","Swelling and pain in the ear"),
            Symptom(38,"耳道腫脹","Ear canal swelling"),
            Symptom(39,"有局部性疼痛","Localized pain"),
            Symptom(40,"視力異常","Visual abnormalities"),
            Symptom(41,"眼睛發紅","Red eyes"),
            Symptom(42,"畏光","Photophobia"),
            Symptom(43,"眼睛疼痛","Eye pain"),
            Symptom(44,"流淚","Tearing"),
            Symptom(45,"眼睛分泌物異常","Abnormal eye discharge"),
            Symptom(46,"眼睛發癢","Itchy eyes"),
            Symptom(47,"視覺異常","Visual disturbances"),
            Symptom(48,"視野異常","Visual field defects"),
            Symptom(49,"精神渙散","Mental confusion"),
            Symptom(50,"咳嗽","Cough"),
            Symptom(51,"呼吸困難","Difficulty breathing"),
            Symptom(52,"喉嚨痛","Sore throat"),
            Symptom(53,"吞嚥不適","Difficulty swallowing"),
            Symptom(54,"扁桃腺腫大","Enlarged tonsils"),
            Symptom(55,"打噴嚏","Sneezing"),
            Symptom(56,"咳痰","Productive cough"),
            Symptom(57,"皮膚出現紅斑","Skin rash"),
            Symptom(58,"聲帶腫脹","Swollen vocal cords"),
            Symptom(59,"聲音異常","Voice changes"),
            Symptom(60,"口腔出現紅白斑或紅斑","Oral red or white patches"),
            Symptom(61,"唇內白斑","Leukoplakia of the lips"),
            Symptom(62,"舌部白斑","Leukoplakia of the tongue"),
            Symptom(63,"食慾不振","Loss of appetite"),
            Symptom(64,"口乾舌燥","Dry mouth"),
            Symptom(65,"牙齦腫痛","Swollen and painful gums"),
            Symptom(66,"吞嚥不適","Difficulty swallowing"),
            Symptom(67,"口腔傷口難以癒合","Oral sores that are slow to heal"),
            Symptom(68,"口腔內部或周圍出現腫塊","Lump in or around the mouth"),
            Symptom(69,"舌部知覺喪失","Loss of sensation in the tongue"),
            Symptom(70,"頸部腫脹","Neck swelling"),
            Symptom(71,"肌肉痠痛","Muscle soreness"),
            Symptom(72,"關節疼痛","Joint pain"),
            Symptom(73,"麻痺感","Numbness"),
            Symptom(74,"呼吸困難","Difficulty breathing"),
            Symptom(75,"胸悶","Chest tightness"),
            Symptom(76,"呼吸聲異常","Abnormal breath sounds"),
            Symptom(77,"咳血","Coughing up blood"),
            Symptom(78,"呼吸困難","Difficulty breathing"),
            Symptom(79,"呼吸急促","Shortness of breath"),
            Symptom(80,"咳血","Coughing up blood"),
            Symptom(81,"胸悶","Chest tightness"),
            Symptom(82,"呼吸困難","Difficulty breathing"),
            Symptom(83,"夜咳","Night cough"),
            Symptom(84,"喉嚨痛","Sore throat"),
            Symptom(85,"咳痰","Productive cough"),
            Symptom(86,"心律不整","Irregular heartbeat"),
            Symptom(87,"非典型心絞痛","Atypical angina"),
            Symptom(88,"心跳異常","Abnormal heartbeats"),
            Symptom(89,"心跳驟停","Cardiac arrest"),
            Symptom(90,"乳頭分泌物","Nipple discharge"),
            Symptom(91,"乳房疼痛","Breast pain"),
            Symptom(92,"乳房腫塊","Breast lump"),
            Symptom(93,"皮膚紅疹","Skin rash"),
            Symptom(94,"腹部脹氣","Abdominal bloating"),
            Symptom(95,"嘔酸水","Acid reflux"),
            Symptom(96,"腹瀉","Diarrhea"),
            Symptom(97,"消化不良","Indigestion"),
            Symptom(98,"水泡","Blisters"),
            Symptom(99,"丘疹","Papules"),
            Symptom(100,"會陰部脫皮","Peeling in the perineal area"),
            Symptom(101,"皮膚發癢","Itchy skin"),
            Symptom(102,"性交疼痛","Painful intercourse"),
            Symptom(103,"尿道不適","Urethral discomfort"),
            Symptom(104,"淋巴結腫大","Enlarged lymph nodes"),
            Symptom(105,"脊椎側彎","Scoliosis"),
            Symptom(106,"血糖異常","Blood glucose abnormalities"),
            Symptom(107,"睡眠品質差","Poor sleep quality"),
            Symptom(108,"情緒不穩","Mood swings"),
            Symptom(109,"腫脹","Swelling")
        )
        val sql3 = "INSERT INTO Symptoms (SymptomID, SymName , En_SymName) VALUES (?, ?, ?)"
        val stmt3 = db.compileStatement(sql3)
        for (symptom in defaultData3) {
            stmt3.bindLong(1, symptom.SymptomID.toLong())
            stmt3.bindString(2, symptom.SymName)
            stmt3.bindString(3, symptom.En_SymName)
            stmt3.executeInsert()
            stmt3.clearBindings()
        }
        // TABLE_SUBPARTSYMPTOMS
        val defaultData4 = listOf(
            SubPartSymptom(1,1),
            SubPartSymptom(1,2),
            SubPartSymptom(1,3),
            SubPartSymptom(1,4),
            SubPartSymptom(1,5),
            SubPartSymptom(1,6),
            SubPartSymptom(1,7),
            SubPartSymptom(1,8),
            SubPartSymptom(1,9),
            SubPartSymptom(2,10),
            SubPartSymptom(2,11),
            SubPartSymptom(2,12),
            SubPartSymptom(2,13),
            SubPartSymptom(2,14),
            SubPartSymptom(2,15),
            SubPartSymptom(2,16),
            SubPartSymptom(2,17),
            SubPartSymptom(2,18),
            SubPartSymptom(2,19),
            SubPartSymptom(3,20),
            SubPartSymptom(3,21),
            SubPartSymptom(3,22),
            SubPartSymptom(3,23),
            SubPartSymptom(3,24),
            SubPartSymptom(3,25),
            SubPartSymptom(3,26),
            SubPartSymptom(3,27),
            SubPartSymptom(3,28),
            SubPartSymptom(3,29),
            SubPartSymptom(4,30),
            SubPartSymptom(4,31),
            SubPartSymptom(4,32),
            SubPartSymptom(4,33),
            SubPartSymptom(4,34),
            SubPartSymptom(4,35),
            SubPartSymptom(4,36),
            SubPartSymptom(4,37),
            SubPartSymptom(4,38),
            SubPartSymptom(4,39),
            SubPartSymptom(5,40),
            SubPartSymptom(5,41),
            SubPartSymptom(5,42),
            SubPartSymptom(5,43),
            SubPartSymptom(5,44),
            SubPartSymptom(5,45),
            SubPartSymptom(5,46),
            SubPartSymptom(5,47),
            SubPartSymptom(5,48),
            SubPartSymptom(5,49),
            SubPartSymptom(6,50),
            SubPartSymptom(6,51),
            SubPartSymptom(6,52),
            SubPartSymptom(6,53),
            SubPartSymptom(6,54),
            SubPartSymptom(6,55),
            SubPartSymptom(6,56),
            SubPartSymptom(6,57),
            SubPartSymptom(6,58),
            SubPartSymptom(6,59),
            SubPartSymptom(7,60),
            SubPartSymptom(7,61),
            SubPartSymptom(7,62),
            SubPartSymptom(7,63),
            SubPartSymptom(7,64),
            SubPartSymptom(7,65),
            SubPartSymptom(7,66),
            SubPartSymptom(7,67),
            SubPartSymptom(7,68),
            SubPartSymptom(7,69),
            SubPartSymptom(8,70),
            SubPartSymptom(8,71),
            SubPartSymptom(8,72),
            SubPartSymptom(8,73),
            SubPartSymptom(9,74),
            SubPartSymptom(9,75),
            SubPartSymptom(9,76),
            SubPartSymptom(9,77),
            SubPartSymptom(10,78),
            SubPartSymptom(10,79),
            SubPartSymptom(10,80),
            SubPartSymptom(10,81),
            SubPartSymptom(11,82),
            SubPartSymptom(11,83),
            SubPartSymptom(11,84),
            SubPartSymptom(11,85),
            SubPartSymptom(12,86),
            SubPartSymptom(12,87),
            SubPartSymptom(12,88),
            SubPartSymptom(12,89),
            SubPartSymptom(13,90),
            SubPartSymptom(13,91),
            SubPartSymptom(13,92),
            SubPartSymptom(13,93),
            SubPartSymptom(14,94),
            SubPartSymptom(14,95),
            SubPartSymptom(14,96),
            SubPartSymptom(14,97),
            SubPartSymptom(15,98),
            SubPartSymptom(15,99),
            SubPartSymptom(15,100),
            SubPartSymptom(15,101),
            SubPartSymptom(15,102),
            SubPartSymptom(27,101),
            SubPartSymptom(29,101),
            SubPartSymptom(54,101),
            SubPartSymptom(56,101),
            SubPartSymptom(85,101),
            SubPartSymptom(87,101),
            SubPartSymptom(112,101),
            SubPartSymptom(114,101),
            SubPartSymptom(16,103),
            SubPartSymptom(17,103),
            SubPartSymptom(74,103),
            SubPartSymptom(75,103),
            SubPartSymptom(18,39),
            SubPartSymptom(22,39),
            SubPartSymptom(23,39),
            SubPartSymptom(45,39),
            SubPartSymptom(49,39),
            SubPartSymptom(50,39),
            SubPartSymptom(76,39),
            SubPartSymptom(80,39),
            SubPartSymptom(81,39),
            SubPartSymptom(103,39),
            SubPartSymptom(107,39),
            SubPartSymptom(108,39),
            SubPartSymptom(19,93),
            SubPartSymptom(19,104),
            SubPartSymptom(24,93),
            SubPartSymptom(24,104),
            SubPartSymptom(46,93),
            SubPartSymptom(46,104),
            SubPartSymptom(51,93),
            SubPartSymptom(51,104),
            SubPartSymptom(77,93),
            SubPartSymptom(77,104),
            SubPartSymptom(82,93),
            SubPartSymptom(82,104),
            SubPartSymptom(104,93),
            SubPartSymptom(104,104),
            SubPartSymptom(109,93),
            SubPartSymptom(109,104),
            SubPartSymptom(20,105),
            SubPartSymptom(47,105),
            SubPartSymptom(78,105),
            SubPartSymptom(105,105),
            SubPartSymptom(21,24),
            SubPartSymptom(48,24),
            SubPartSymptom(79,24),
            SubPartSymptom(106,24),
            SubPartSymptom(25,106),
            SubPartSymptom(26,20),
            SubPartSymptom(52,106),
            SubPartSymptom(53,20),
            SubPartSymptom(83,106),
            SubPartSymptom(84,20),
            SubPartSymptom(110,106)
        )
        val sql4 = "INSERT INTO SubPartSymptoms (SubPartID, SymptomID) VALUES (?, ?)"
        val stmt4 = db.compileStatement(sql4)
        for (subpartsymptom in defaultData4) {
            stmt4.bindLong(1, subpartsymptom.SubPartID.toLong())
            stmt4.bindLong(2, subpartsymptom.SymptomID.toLong())
            stmt4.executeInsert()
            stmt4.clearBindings()
        }
        // TABLE_DEPARTMENTS
        val defaultData5 = listOf(
            Department(1,"家庭醫學科","Family Medicine","不分年齡、男女老少，任何疾病或症狀未明者均可看診，特別適於初診篩檢或長期慢性病的診療。除個人就診外，更提供全家的醫療及保健服務。","Regardless of age or gender, anyone with unclear diseases or symptoms can seek consultation. It is especially suitable for initial screening or the treatment of long-term chronic diseases. In addition to individual consultations, comprehensive medical and healthcare services are also provided for the whole family."),
            Department(2,"神經內科","Neurology","頭痛、頸痛、腰酸背痛、神經痛、坐骨神經痛、椎間盤突出、肌肉疼痛、手麻腳麻、四肢無力、頭暈、昏厥、抽搐、痙攣、癲癇、手腳顫抖、不自主運動、步態不穩、巴金森氏症、中風、半身不遂、意識障礙、癡呆、顏面神經麻痺、感覺異常、腦炎、腦膜炎、肌肉病變、神經病變、腦瘤、多發性硬化、腦麻痹、睡眠疾病、各種失智症","Headache, neck pain, back pain, neuralgia, sciatica, disc herniation, muscle pain, numbness in hands and feet, weakness in limbs, dizziness, fainting, seizures, spasms, epilepsy, tremors in hands and feet, involuntary movements, unsteady gait, Parkinson's disease, stroke, hemiplegia, consciousness disorders, dementia, facial nerve palsy, sensory abnormalities, encephalitis, meningitis, muscle diseases, neuropathy, brain tumors, multiple sclerosis, cerebral palsy, sleep disorders, and various types of dementia."),
            Department(3,"一般外科","General Surgery","一般外傷、靜脈曲張、乳房有硬塊、甲狀腺腫大、表皮及軟部組織腫瘤、疝氣及腹腔內器官需手術(胃腸出血及腫瘤、腸阻塞、盲腸炎、腹膜炎、肝膽腫瘤、膽道結石、脾臟及胰臟疾病、腹腔內腫瘤)、便血、痔瘡。","General trauma, varicose veins, breast lumps, thyroid enlargement, tumors of the epidermis and soft tissues, hernias, and intra-abdominal organ surgery (gastrointestinal bleeding and tumors, intestinal obstruction, appendicitis, peritonitis, liver and bile duct tumors, gallstones, spleen and pancreatic diseases, intra-abdominal tumors), rectal bleeding, and hemorrhoids."),
            Department(4,"一般內科","General Internal Medicine","凡十五歲以上，覺得身體內不舒服者，皆可看診。","Anyone aged 15 and above who feels unwell can seek consultation."),
            Department(5,"復健部","Rehabilitation Department","學習障礙，骨骼肌肉神經病變，腰酸背痛，關節疼痛，頸腰椎骨刺，坐骨神經痛，手腳痠麻，顏面神經麻痺，語言障礙，運動傷害，腦中風，頸傷，脊椎損傷，小兒麻痺，腦性麻痺，灼傷及外傷引致的功能或活動障礙之肢體復健，截肢後復健訓練。","Learning disabilities, skeletal muscle and nerve disorders, back pain, joint pain, cervical and lumbar bone spurs, sciatica, limb numbness, facial nerve palsy, speech disorders, sports injuries, stroke, neck injuries, spinal injuries, poliomyelitis, cerebral palsy, limb rehabilitation for functional or mobility impairments caused by burns and trauma, and rehabilitation training after amputation."),
            Department(6,"肝膽腸胃科","Hepatobiliary and Gastroenterology","任何原因所引起之腹痛，腹脹，腹部不舒服，大便不規則及腹瀉，便秘及十二指腸，食慾不振，胃潰瘍，黑色便，吐血，吞嚥困難，皮膚或小變呈黃，肝機能異常，肝膽結石，疑腹內腫瘤，疑肝硬化，疑肝腫瘤。","Abdominal pain, bloating, abdominal discomfort, irregular bowel movements and diarrhea, constipation and duodenal issues, loss of appetite, stomach ulcers, black stools, vomiting blood, difficulty swallowing, jaundice (yellowing of the skin or eyes), abnormal liver function, gallstones, suspected intra-abdominal tumors, suspected liver cirrhosis, and suspected liver tumors caused by any reason."),
            Department(7,"皮膚科","Dermatology","一般皮膚治療包括：濕疹、皮膚過敏、毛髮指甲的病變。皮膚感染包括：皮膚、黴菌、細菌病毒之感染。青春痘、老人斑、黑斑治療、皮膚腫瘤切除。","General skin treatments include eczema, skin allergies, and hair and nail disorders. Skin infections include infections of the skin, fungi, and bacterial and viral infections. Treatment of acne, age spots, dark spots, and removal of skin tumors."),
            Department(8,"感染科","Infectious Disease","各種原因導致之發燒，各種病菌引發之炎症及感染，例如氣管炎、上呼吸道感染(感冒)、肺炎、腎炎、膀胱炎、腦膜炎、心臟內膜炎、膽囊炎、膽道炎、蜂窩組織炎、骨隨炎、敗血症。","Fever caused by various reasons, and inflammation and infections caused by various pathogens, such as bronchitis, upper respiratory tract infections (colds), pneumonia, nephritis, cystitis, meningitis, endocarditis, cholecystitis, cholangitis, cellulitis, osteomyelitis, and sepsis."),
            Department(9,"耳鼻喉科","Otorhinolaryngology (ENT)","聲音沙啞、吞嚥困難、鼻塞、鼻竇炎、鼻過敏、扁桃腺病變、耳鳴、暈眩、聽力障礙、音聲障礙、頭頸部腫瘤、舌及口腔咽喉疾病，打鼾，鼻瘜肉。","Hoarseness, difficulty swallowing, nasal congestion, sinusitis, nasal allergies, tonsil disorders, tinnitus, dizziness, hearing impairment, voice disorders, head and neck tumors, tongue and oral throat diseases, snoring, and nasal polyps."),
            Department(10,"鼻科","Rhinology","鼻竇炎，過敏性鼻炎，鼻瘜肉，鼻中隔偏曲，鼻出血，嗅覺障礙，腫瘤。","Sinusitis, allergic rhinitis, nasal polyps, deviated nasal septum, nosebleeds, olfactory disorders, and tumors."),
            Department(11,"身心治療科","Psychosomatic Medicine","壓力測試、失眠、焦慮、憂鬱、腦神經衰弱、記憶力減退、老年失智、癡呆、自殺、婚姻家庭問題、人際互動問題、幻聽、妄想、精神分裂症、躁鬱症、停經症候群、經前症候群、器質性疾病、酒癮、厭食、暴食。","Stress tests, insomnia, anxiety, depression, neurasthenia, memory decline, senile dementia, dementia, suicidal tendencies, marital and family issues, interpersonal problems, hallucinations, delusions, schizophrenia, bipolar disorder, menopause syndrome, premenstrual syndrome, organic diseases, alcohol addiction, anorexia, and binge eating."),
            Department(12,"睡眠中心","Sleep Center","失眠、睡眠呼吸暫停、不寧腿綜合症、發作性睡病、晝夜節律睡眠障礙。","Insomnia, sleep apnea, restless legs syndrome, narcolepsy, and circadian rhythm sleep disorders."),
            Department(13,"過敏免疫風濕科","Allergy, Immunology, and Rheumatology","關節炎（痛風、類風濕性、退化性、感染性等），脊椎炎、皮膚過敏、頸部僵硬、風濕痛、腰背肩痛、氣喘、過敏性鼻炎、藥物食物等過敏。紅斑性狼瘡、硬皮症、皮肌炎、血管炎等膠原血管和自體免疫疾病、僵直性脊椎炎、肌肉、肌腱疼痛、五十肩。","Arthritis (gout, rheumatoid, degenerative, infectious, etc.), spondylitis, skin allergies, neck stiffness, rheumatic pain, back and shoulder pain, asthma, allergic rhinitis, drug and food allergies. Lupus erythematosus, scleroderma, dermatomyositis, vasculitis, and other collagen vascular and autoimmune diseases, ankylosing spondylitis, muscle and tendon pain, and frozen shoulder."),
            Department(14,"胸腔內科","Pulmonology","感冒、咳嗽、咳血、呼吸道感染、支氣管炎、肺炎、胸痛、胸悶、呼吸困難、哮喘症、肺氣腫、肺結核、肺膿瘍、肺腫瘤、肺塵症、職業性肺疾病、呼吸道異物取出、支氣管擴張症等各種肺部疾病，睡眠呼吸中止症。","Colds, cough, hemoptysis, respiratory infections, bronchitis, pneumonia, chest pain, chest tightness, difficulty breathing, asthma, emphysema, tuberculosis, lung abscess, lung tumors, pneumoconiosis, occupational lung diseases, removal of foreign objects from the respiratory tract, bronchiectasis, and various other lung diseases, and sleep apnea."),
            Department(15,"耳科","Otology","中耳炎、外耳炎、傳導性聽力損失、感音神經性聽力損失、耳鳴、梅尼爾病、良性陣發性位置性眩暈、耳硬化症、耳部腫瘤。","Otitis media, otitis externa, conductive hearing loss, sensorineural hearing loss, tinnitus, Meniere's disease, benign paroxysmal positional vertigo, otosclerosis, and ear tumors."),
            Department(16,"眼科","Ophthalmology","弱視、近視、斜視的患者作檢查、訓練、手術矯正治療及驗光 配鏡等、飛蚊症、玻璃體出血、視網膜出血、破裂或剝離、視網膜發炎變性、視神經炎、眼壓過高、急性或慢性青光眼、青光眼手術及雷射治療、白內障超音波乳化手術。","Examination, training, surgical correction, and prescription of glasses for patients with amblyopia, myopia, and strabismus; treatment of floaters, vitreous hemorrhage, retinal hemorrhage, detachment or rupture, retinal inflammation or degeneration, optic neuritis, high intraocular pressure, acute or chronic glaucoma, glaucoma surgery and laser treatment, and cataract phacoemulsification surgery."),
            Department(17,"美容醫學科","Aesthetic Medicine","雙眼皮、眼袋、果酸換膚、臉部拉皮、隆鼻、隆乳、腹部整型、全身抽脂手術、雷射美容、去斑、微晶磨皮、腹部拉皮。","Double eyelid surgery, eye bag removal, chemical peels, facelift, rhinoplasty, breast augmentation, abdominal contouring, full-body liposuction, laser cosmetic treatments, spot removal, microdermabrasion, and abdominoplasty."),
            Department(18,"血液腫瘤科","Hematology and Oncology","一般常規血液或血球檢驗、貧血、紅血球異常、白血球過多、白血球不足、血小板過多或過少、血癌、淋巴腺腫大、淋巴瘤、骨髓瘤及其他骨髓異常、紫瘢、不明原因出血、血液凝固異常、多血症、全部血球不足症，及不明原因皮下腫塊，或其他疑似血液，或血球異常之疾病者。","General routine blood or hematologic tests, anemia, red blood cell abnormalities, leukocytosis, leukopenia, thrombocytosis, thrombocytopenia, leukemia, lymphadenopathy, lymphoma, multiple myeloma and other bone marrow abnormalities, purpura, unexplained bleeding, coagulation disorders, polycythemia, pancytopenia, and unexplained subcutaneous lumps, or other suspected blood or hematologic abnormalities."),
            Department(19,"整形外科","Plastic Surgery","先天性畸形、各項美容整型手術、傷殘或腫瘤、乳房重整、皮膚腫瘤、手部外科、肢體嚴重外傷或潰爛、頭頸部腫瘤、灼燙傷各種疤痕整型、顱顏外科。","Congenital malformations, various cosmetic surgery procedures, injury or tumor, breast reconstruction, skin tumors, hand surgery, severe limb trauma or ulcers, head and neck tumors, burn scars of various types, and craniofacial surgery."),
            Department(20,"放射治療科","Radiation Oncology","中樞神經系統、頭頸部、胸腔、腹腔、生殖泌尿系統、婦科、乳房等各部位惡性腫瘤之根治性、輔助性或治標性放射治療。各類非惡性組織增之治療性或預防性放射治療。癌病篩檢及診斷。癌病咨詢服務。癌病追蹤檢查。","Radical, adjuvant, or palliative radiotherapy for malignant tumors of the central nervous system, head and neck, thoracic cavity, abdominal cavity, reproductive and urinary systems, gynecology, breast, and other areas. Therapeutic or preventive radiotherapy for various benign tissue growths. Cancer screening and diagnosis. Cancer consultation services. Cancer follow-up examinations."),
            Department(21,"心臟血管內科","Cardiovascular Medicine","先天性心贓病，風溼性心臟病，冠狀血管心臟病，心肌病變，高血壓，心律不整以及各種心臟血管病變所引起的心臟衰竭，昏倒，休克等。","Congenital heart disease, rheumatic heart disease, coronary artery disease, cardiomyopathy, hypertension, arrhythmias, and heart failure, fainting, shock, and other conditions caused by various cardiovascular disorders."),
            Department(22,"心臟血管外科","Cardiovascular Surgery","靜脈曲張、靜脈浮腫、皮膚炎、腳步浮腫、心律不整(心悸、心跳過慢)、胸悶、胸痛、先天性心臟病活動時呼吸困難、尿毒症之動脈廔管手術、間歇性跛行、血管瘤、動脈瘤、心臟移植、末期心臟病。","Varicose veins, venous edema, dermatitis, foot swelling, arrhythmias (palpitations, bradycardia), chest tightness, chest pain, congenital heart disease with difficulty breathing during activity, arteriovenous fistula surgery for uremia, intermittent claudication, vascular tumors, aneurysms, heart transplantation, and end-stage heart disease."),
            Department(23,"兒童醫學部","Pediatrics","凡十五歲以下，身體不適者皆可掛一般小兒科。","Anyone under the age of 15 who feels unwell can visit the general pediatrics department."),
            Department(24,"胃腸肝膽科","Gastroenterology and Hepatology","任何原因所引起之腹痛，腹脹，腹部不舒服，大便不規則及腹瀉，便秘及十二指腸，食慾不振，胃潰瘍，黑色便，吐血，吞嚥困難，皮膚或小便呈黃，肝機能異常，肝膽結石，疑腹內腫瘤，疑肝硬化，疑肝腫瘤，肝膽結石。","Abdominal pain, bloating, abdominal discomfort, irregular bowel movements and diarrhea, constipation and duodenal issues, loss of appetite, stomach ulcers, black stools, vomiting blood, difficulty swallowing, jaundice (yellowing of the skin or urine), abnormal liver function, gallstones, suspected intra-abdominal tumors, suspected liver cirrhosis, suspected liver tumors, and gallstones."),
            Department(25,"泌尿部","Urology Department","腎臟移植、泌尿道感染、泌尿道外傷、先天性泌尿系統異常、泌尿道腫瘤、前列腺肥大、腎結石、膀胱結石、血尿、頻尿、小便無力、尿道下裂、隱睪、陰囊腫痛、夜尿、女性尿失禁、男性不孕、男性性機能異常、包皮、性病、膀胱機能異常、內視鏡手術、碎石機手術。","Kidney transplant, urinary tract infections, urinary tract trauma, congenital urinary system abnormalities, urinary tract tumors, prostate hypertrophy, kidney stones, bladder stones, hematuria, frequent urination, weak urine stream, hypospadias, cryptorchidism, scrotal swelling and pain, nocturia, female urinary incontinence, male infertility, male sexual dysfunction, foreskin issues, sexually transmitted diseases, bladder dysfunction, endoscopic surgery, and lithotripsy."),
            Department(26,"腎臟科","Nephrology","腎臟炎，血尿，尿蛋白，水腫，尿毒症，腎結石，尿路感染，電解質不平衡，洗腎，高血壓，腎移植追蹤。","Nephritis, hematuria, proteinuria, edema, uremia, kidney stones, urinary tract infections, electrolyte imbalance, dialysis, hypertension, and kidney transplant follow-up."),
            Department(27,"新陳代謝科","Metabolism","疲勞感、體重問題（無法解釋的體重增加或減少）、血糖問題（如糖尿病前期、糖尿病或低血糖的症狀）、甲狀腺功能異常（包括甲狀腺功能亢進或甲狀腺功能減退）、高膽固醇或高血脂、高血壓、骨質疏鬆、荷爾蒙失調（如多囊卵巢綜合症或男性荷爾蒙低下）、皮膚變化（如皮膚異常乾燥、粗糙或有色素沉著）、消化問題（如胃腸功能異常）。","Fatigue, weight issues (unexplained weight gain or loss), blood sugar problems (such as prediabetes, diabetes, or hypoglycemia), thyroid dysfunction (including hyperthyroidism or hypothyroidism), high cholesterol or high blood lipids, hypertension, osteoporosis, hormonal imbalances (such as polycystic ovary syndrome or low male hormones), skin changes (such as unusually dry, rough, or discolored skin), and digestive problems (such as gastrointestinal dysfunction)."),
            Department(28,"心臟衰竭門診","Heart Failure Clinic","呼吸困難、疲勞感、體重快速增加、腳踝和腿部腫脹（浮腫）、夜間頻繁排尿、心跳異常（如心跳過快或不規則）、咳嗽或喘息（尤其是夜間或平躺時）、食慾不振或消化不良、持續咳嗽或帶有粉紅色泡沫的痰液、胸痛或胸悶、注意力不集中或意識模糊。","Difficulty breathing, fatigue, rapid weight gain, ankle and leg swelling (edema), frequent nighttime urination, abnormal heartbeat (such as rapid or irregular heartbeat), coughing or wheezing (especially at night or when lying down), loss of appetite or indigestion, persistent cough or pink frothy sputum, chest pain or tightness, difficulty concentrating or mental confusion."),
            Department(29,"神經外科","Neurosurgery","腦神經創傷，各種腦血管病變，腦出血，水腦症，脊椎骨折、脊椎損傷、脊椎骨刺、脊椎移位、軟骨突出、多汗症、各種神經疼痛（如頭痛、三叉神經痛、頸痛、背痛及坐骨神經痛等）、四肢酸麻及各種神經無力。專精腦瘤及腦血管之顯微手術及內視鏡手術、顯微脊椎手術、多汗症內視鏡手術等。","Brain nerve trauma, various cerebrovascular diseases, cerebral hemorrhage, hydrocephalus, spinal fractures, spinal injuries, spinal bone spurs, spinal dislocation, herniated disc, hyperhidrosis, various nerve pains (such as headaches, trigeminal neuralgia, neck pain, back pain, and sciatica), limb numbness and various types of nerve weakness. Specializing in microsurgery and endoscopic surgery for brain tumors and cerebrovascular conditions, microsurgical spinal procedures, and endoscopic surgery for hyperhidrosis."),
            Department(30,"胸腔外科","Thoracic Surgery","胸腔、食道、肺腫瘤、胸腔腫瘤、畸形、膿胸、血胸、氣胸、胸部外傷、食道阻塞（腫瘤）、縱膈腔腫瘤、橫膈病變及任何 吞嚥困難，呼吸窘迫，胸壁凹陷或凸出之變形，咳血症狀者。","Thoracic, esophageal, and lung tumors; thoracic tumors; deformities; empyema; hemothorax; pneumothorax; chest trauma; esophageal obstruction (tumor); mediastinal tumors; diaphragmatic disorders; and any cases of difficulty swallowing, respiratory distress, chest wall deformities (concave or convex), and hemoptysis."),
            Department(31,"大腸直腸外科","Colorectal Surgery","腹痛、排便習慣改變（如便秘或腹瀉）、便血或排出黑色糞便、持續的腹脹或腹部不適、體重無故減輕、貧血（尤其是缺鐵性貧血）、糞便形狀變細或不成形、頻繁感到排便不完全、肛門疼痛或瘙癢、肛門出血或膿液分泌、肛裂或痔瘡、直腸脫垂或肛門脫垂。","Abdominal pain, changes in bowel habits (such as constipation or diarrhea), rectal bleeding or black stools, persistent bloating or discomfort in the abdomen, unexplained weight loss, anemia (especially iron-deficiency anemia), narrow or malformed stools, frequent sensation of incomplete bowel movements, anal pain or itching, anal bleeding or discharge of pus, anal fissures or hemorrhoids, rectal prolapse or anal prolapse."),
            Department(32,"婦產部","Obstetrics and Gynecology","孕婦產前後檢查，不規則陰道出血、白帶或不正常分泌物、月經失調、尿失禁、不孕症、女性腹部疼痛、婦科腫瘤、避孕、優生保健諮詢、人工流產、女性結紮、例行婦科檢查和子宮頸抹片、乳房疼痛或硬塊、人工受孕、精液檢查、精子分離、羊膜腔穿刺術、染色體檢查、遺傳諮詢。","Prenatal and postnatal examinations for pregnant women, irregular vaginal bleeding, abnormal vaginal discharge, menstrual irregularities, urinary incontinence, infertility, abdominal pain in females, gynecological tumors, contraception, prenatal care counseling, induced abortion, female sterilization, routine gynecological examinations and Pap smears, breast pain or lumps, artificial insemination, semen analysis, sperm separation, amniocentesis, chromosomal testing, genetic counseling."),
            Department(33,"牙科","Dentistry","包括齲齒填補、磨耗、蝕損牙齒之復形、牙齒漂白、根管治療、兒童牙科、齒顎矯正、根管治療、假牙膺復（固定和活動局部 或全口假牙之製作）、牙周病、口腔外科等一般性牙科治療。","General dental treatments including dental fillings for cavities, restoration of worn or damaged teeth, teeth whitening, root canal therapy, pediatric dentistry, orthodontics, dentures (both fixed and removable partial or full dentures), periodontal disease treatment, oral surgery."),
            Department(34,"喉及頭頸外科","Laryngology and Head & Neck Surgery","持續性的喉嚨痛或異物感、聲音嘶啞或改變、吞嚥困難或疼痛、頸部或喉部腫塊、持續的耳痛或耳鳴、鼻塞或鼻出血、持續性鼻涕倒流、口腔內或舌頭上的不明原因潰瘍或腫塊、呼吸困難或喘息、未解釋的體重減輕、持續性的口臭、面部或頸部的麻木或無力、頭痛或顏面疼痛、顏面或頸部的皮膚異常變化（如顏色改變或潰瘍）。","Persistent sore throat or sensation of a foreign object, hoarseness or voice changes, difficulty or pain when swallowing, lump in the neck or throat, persistent earache or ringing in the ears, nasal congestion or nosebleeds, persistent postnasal drip, ulcers or lumps in the mouth or on the tongue for unknown reasons, difficulty breathing or wheezing, unexplained weight loss, persistent bad breath, numbness or weakness in the face or neck, headache or facial pain, abnormal changes in the skin of the face or neck (such as color changes or ulcers)."),
            Department(35,"聽覺科","Audiology","聽力減退或喪失、耳鳴（持續性的耳鳴聲）、耳痛或不適、耳部流膿或液體、耳塞感或耳朵有壓力感、頭暈或眩暈、平衡問題、耳鳴、耳部感染（如中耳炎）、頻繁摳耳或抓耳、語言發展遲緩（尤其是兒童）、聽覺過敏或聽覺敏感、耳朵內異物感或異物、耳垢堆積過多。","Hearing loss or deafness, tinnitus (persistent ringing in the ears), earache or discomfort, oozing or fluid in the ears, feeling of ear fullness or pressure, dizziness or vertigo, balance problems, ear infections (such as otitis media), frequent ear picking or scratching, delayed language development (especially in children), auditory hypersensitivity or sensitivity, sensation of foreign object or foreign body in the ear, excessive accumulation of earwax."),
            Department(36,"噪音醫學中心","Noise Medicine Center","暫無資料","No data available."),
            Department(37,"人工電子耳及助聽器特","Cochlear Implants and Hearing Aids","暫無資料","No data available."),
            Department(38,"打鼾及呼吸中止症","Snoring and Sleep Apnea","暫無資料","No data available."),
            Department(39,"骨科部","Orthopedics Department","骨折、脫臼、骨髓炎、關節退化、腰酸背痛、關節炎、骨畸形、骨腫瘤、脊椎病變、小兒骨關節異常、肌肉、肌腱、筋膜疾病、脊椎骨外傷、坐骨神經痛、四肢酸痛、筋骨疼痛。","Fractures, dislocations, osteomyelitis, joint degeneration, lower back pain, arthritis, bone deformities, bone tumors, spinal disorders, pediatric orthopedic conditions, muscle, tendon, fascia diseases, spinal trauma, sciatica, limb soreness, musculoskeletal pain."),
            Department(40,"兒童青少年暨婦女身心","Child, Adolescent, and Women's Mental Health","持續的焦慮或憂鬱情緒、情緒波動或易怒、注意力不集中或過動、學習困難或成績下降、睡眠問題（如失眠或噩夢）、社交困難或孤立、行為問題（如反抗或攻擊行為）、食慾改變或體重波動、身體症狀無法解釋（如頭痛或腹痛）、自我傷害或自殺念頭、壓力過大或適應困難、強迫行為或重複性思維、產後憂鬱或焦慮、月經相關的情緒問題（如經前焦慮或情緒低落）、性健康或生殖健康相關的心理困擾。","Persistent anxiety or depressive mood, mood swings or irritability, lack of concentration or hyperactivity, learning difficulties or declining academic performance, sleep problems (such as insomnia or nightmares), social difficulties or isolation, behavioral problems (such as defiance or aggressive behavior), changes in appetite or weight fluctuations, unexplained physical symptoms (such as headaches or abdominal pain), self-harm or suicidal thoughts, excessive stress or difficulty coping, obsessive-compulsive behavior or repetitive thinking, postpartum depression or anxiety, menstrual-related emotional issues (such as premenstrual anxiety or mood swings), psychological distress related to sexual or reproductive health."),
            Department(41,"老年精神科","Geriatric Psychiatry","記憶力減退或認知功能下降、混亂或意識模糊、情緒低落或抑鬱、焦慮或過度擔憂、幻覺或妄想、失眠或睡眠障礙、興趣喪失或活力下降、社交孤立或退縮、食慾改變或體重波動、行為改變或異常（如攻擊性行為或多疑）、無法應對日常活動或生活自理困難、藥物濫用或依賴、慢性疼痛或其他無法解釋的身體症狀、頻繁的情緒波動或情緒不穩定、對環境或人際關係的適應困難。","Memory loss or cognitive decline, confusion or mental fog, feelings of sadness or depression, anxiety or excessive worry, hallucinations or delusions, insomnia or sleep disturbances, loss of interest or decreased energy, social isolation or withdrawal, changes in appetite or weight fluctuations, changes in behavior or abnormalities (such as aggression or paranoia), difficulty coping with daily activities or self-care, substance abuse or dependence, chronic pain or other unexplained physical symptoms, frequent mood swings or emotional instability, difficulty adapting to environment or interpersonal relationships."),
            Department(42,"記憶特別門診","Memory Clinic","記憶力減退或喪失、難以記住最近發生的事件、反覆遺忘重要事項或約會、重複提問或講述同一件事、難以完成日常任務或複雜活動、語言表達困難（如找不到合適詞語或理解他人講話困難）、方向感喪失或迷路、情緒波動或行為改變（如易怒或冷漠）、注意力不集中或專注困難、判斷力下降或做出不合常理決定、個性或社交行為顯著改變、失去興趣或活力減退、日常生活自理能力下降（如穿衣或洗澡困難）。","Memory loss or impairment, difficulty remembering recent events, repeated forgetting of important tasks or appointments, repetitive questioning or telling the same thing, difficulty completing daily tasks or complex activities, language difficulties (such as difficulty finding the right words or understanding others), loss of sense of direction or getting lost, mood swings or changes in behavior (such as irritability or apathy), difficulty concentrating or focusing, decreased judgment or making irrational decisions, significant changes in personality or social behavior, loss of interest or decreased energy, decline in ability to perform daily self-care tasks (such as dressing or bathing)."),
            Department(43,"自費心理諮詢","Private Psychological Counseling","暫無資料","No data available."),
            Department(44,"營養治療科","Nutrition Therapy","體重過重或肥胖、體重過輕或營養不良、慢性疾病管理（如糖尿病、高血壓、高膽固醇）、消化系統問題（如腸易激綜合症、克羅恩病、潰瘍性結腸炎）、食物過敏或不耐受、食慾不振或暴飲暴食、營養缺乏（如維生素或礦物質不足）、術後或疾病恢復期的營養支持、飲食失調（如厭食症、暴食症）、兒童及青少年的生長發育問題、孕期或哺乳期的營養需求、老年人的營養健康維護。","Overweight or obesity, underweight or malnutrition, management of chronic diseases (such as diabetes, hypertension, high cholesterol), digestive system issues (such as irritable bowel syndrome, Crohn's disease, ulcerative colitis), food allergies or intolerances, loss of appetite or binge eating, nutritional deficiencies (such as vitamin or mineral deficiencies), nutritional support during postoperative or illness recovery periods, eating disorders (such as anorexia nervosa, bulimia nervosa), growth and development issues in children and adolescents, nutritional needs during pregnancy or lactation, nutritional health maintenance in the elderly."),
            Department(45,"中醫科","Traditional Chinese Medicine","或風濕病、慢性疼痛（如腰痛、肩頸痛）、免疫力低下、呼吸系統疾病（如慢性咳嗽、哮喘）、皮膚問題（如濕疹、皮膚瘙癢）、焦慮或抑鬱、過敏性疾病（如過敏性鼻炎、過敏性皮炎）、體重管理、失眠、以及全身不適或亞健康狀態。","Rheumatic diseases, chronic pain (such as lower back pain, shoulder and neck pain), weakened immune system, respiratory system diseases (such as chronic cough, asthma), skin problems (such as eczema, itching), anxiety or depression, allergic diseases (such as allergic rhinitis, allergic dermatitis), weight management, insomnia, and overall discomfort or suboptimal health status."),
            Department(46,"疼痛治療科","Pain Management","持續性或慢性疼痛（如長期的背痛或頸痛）、關節痛（如膝蓋痛、肩膀痛）、頭痛或偏頭痛、神經性疼痛（如坐骨神經痛、帶狀皰疹後神經痛）、術後疼痛或外傷後疼痛、纖維肌痛、癌症相關疼痛、風濕病或類風濕性關節炎引起的疼痛、腰椎間盤突出引起的疼痛、肌肉疼痛或痙攣、局部疼痛（如手腕痛、足底痛）、疼痛伴隨的睡眠困難或情緒問題（如焦慮或抑鬱）。","Chronic or persistent pain (such as long-term back pain or neck pain), joint pain (such as knee pain, shoulder pain), headaches or migraines, neuropathic pain (such as sciatica, post-herpetic neuralgia), postoperative pain or pain from injuries, fibromyalgia, cancer-related pain, pain from rheumatic diseases or rheumatoid arthritis, pain from lumbar disc herniation, muscle pain or spasms, localized pain (such as wrist pain, plantar fasciitis), pain accompanied by sleep difficulties or emotional issues (such as anxiety or depression).")
        )
        val sql5 = "INSERT INTO Departments (DepartmentID, DpmName, En_DpmName, Description, En_Description) VALUES (?, ?, ?, ?, ?)"
        val stmt5 = db.compileStatement(sql5)
        for (department in defaultData5) {
            stmt5.bindLong(1, department.DepartmentID.toLong())
            stmt5.bindString(2, department.DpmName)
            stmt5.bindString(3, department.En_DpmName)
            stmt5.bindString(4, department.Description)
            stmt5.bindString(5, department.En_Description)
            stmt5.executeInsert()
            stmt5.clearBindings()
        }
        // TABLE_SYMPTOMDEPARTMENTS
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

        // 更新選擇條件以包括 part_name 或 en_part_name 和 Gender
        val selection = "(PartName = ? OR En_PartName = ?) AND Gender = ?"
        val selectionArgs = arrayOf(PartName, PartName, Gender.toString())
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
    /**
     * 根據部位細節ID查詢相關的症狀完整資料。
     * @param subPartId 部位細節的ID。
     * @return 返回一個包含症狀完整資料的列表，每個元素包含症狀ID、症狀名稱及其英文名稱。
     */
    fun getSymptomsBySubPartId(subPartId: Int): List<Symptom> {
        val symptoms = mutableListOf<Symptom>() // 儲存查詢到的症狀資訊
        val db = this.readableDatabase // 獲取可讀的資料庫實例
        val query = """
        SELECT Symptoms.SymptomID, Symptoms.SymName, Symptoms.En_SymName
        FROM SubPartSymptoms
        JOIN Symptoms ON SubPartSymptoms.SymptomID = Symptoms.SymptomID
        WHERE SubPartSymptoms.SubPartID = ?
        """ // SQL查詢語句
        val cursor = db.rawQuery(query, arrayOf(subPartId.toString())) // 執行查詢
        while (cursor.moveToNext()) {
            val symptomId = cursor.getInt(cursor.getColumnIndexOrThrow("SymptomID"))
            val symName = cursor.getString(cursor.getColumnIndexOrThrow("SymName"))
            val enSymName = cursor.getString(cursor.getColumnIndexOrThrow("En_SymName"))
            symptoms.add(Symptom(symptomId, symName, enSymName)) // 創建Symptom物件並添加到列表
        }
        cursor.close() // 關閉游標
        db.close() // 關閉資料庫實例
        return symptoms // 返回包含症狀資料的列表
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
data class Department(
    val DepartmentID: Int,
    val DpmName: String,
    val En_DpmName: String,
    val Description: String,
    val En_Description: String
)
data class Symptom(
    val SymptomID: Int,
    val SymName: String,
    val En_SymName: String
)
data class SymptomDepartment(
    val SymptomID: Int,
    val DepartmentID: Int
)
data class SubPartSymptom(
    val SubPartID: Int,
    val SymptomID: Int
)