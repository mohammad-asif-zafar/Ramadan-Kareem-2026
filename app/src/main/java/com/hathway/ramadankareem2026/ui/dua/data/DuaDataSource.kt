package com.hathway.ramadankareem2026.ui.dua.data

import com.hathway.ramadankareem2026.ui.dua.model.DuaItem
import com.hathway.ramadankareem2026.ui.dua.model.LocalizedDuaText


object DuaDataSource {

    //  Ramadan
    val ramadanDuas = listOf(

        DuaItem(
            id = "ramadan_moon",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Ramadan Moon Sighting",
                hindi = "रमज़ान के चांद देखने की दुआ",
                urdu = "رمضان کے چاند دیکھنے کی دعا",
                malaysian = "Doa Melihat Bulan Ramadan"
            ),
            arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا بِالْيُمْنِ وَالإِيمَانِ وَالسَّلَامَةِ وَالإِسْلَامِ",
            transliteration = "Allahumma ahlilhu 'alaynā bil-yumni wal-īmān was-salāmati wal-islām",
            translation = LocalizedDuaText(
                english = "O Allah, bring it upon us with blessing, faith, safety, and Islam.",
                hindi = "ऐ अल्लाह, हम पर बरकत, ईमान, सुरक्षा और इस्लाम लाए।",
                urdu = "اے اللہ، ہمیں پر برکت، ایمان، حفاظت اور اسلام لا۔",
                malaysian = "Wahai Allah, bawakan kami dengan keberkatan, iman, keselamatan, dan Islam."
            ),
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "sehri",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Intention for Fasting (Sehri - 1)",
                hindi = "रोज़े रखने की नियत (सेहरी - 1)",
                urdu = "روزے رکھنے کی نیت (سہری - 1)",
                malaysian = "Niat Berpuasa (Sahur - 1)"
            ),
            arabic = "بِسْمِ اللَّهِ",
            transliteration = "Bismillah",
            translation = LocalizedDuaText(
                english = "In the name of Allah",
                hindi = "अल्लाह के नाम से",
                urdu = "اللہ کے نام سے",
                malaysian = "Dengan nama Allah"
            ),
            source = "Fiqh"
        ),

        DuaItem(
            id = "sehri_2",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Intention for Fasting (Sehri - 2)",
                hindi = "रोज़े रखने की नियत (सेहरी - 2)",
                urdu = "روزے رکھنے کی نیت (سہری - 2)",
                malaysian = "Niat Berpuasa (Sahur - 2)"
            ),
            arabic = "وَبِصَوْمِ غَدٍ نَوَيْتُ مِنْ شَهْرِ رَمَضَانَ",
            transliteration = "Wa bisawmi ghadin nawaitu min shahri Ramaḍān",
            translation = LocalizedDuaText(
                english = "I intend to keep fast for tomorrow in month of Ramadan.",
                hindi = "मैं कल रमज़ान के महीने में रोज़ा रखने का इरादा रखता हूँ।",
                urdu = "میں کل رمضان کے مہینے میں روزہ رکھنے کا ارادہ رکھتا ہوں۔",
                malaysian = "Saya berniat untuk berpuasa esok dalam bulan Ramadan."
            ),
            source = "Fiqh"
        ),

        DuaItem(
            id = "iftar",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Duʿāʾ at Iftar - 1",
                hindi = "इफ्तार की दुआ - 1",
                urdu = "افطار کی دعا - 1",
                malaysian = "Doa Berbuka Puasa - 1"
            ),
            arabic = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الْأَجْرُ",
            transliteration = "Dhahabaẓ-ẓama' u wabtallatil-'urūq wa thabatal-ajru",
            translation = LocalizedDuaText(
                english = "The thirst has gone, the veins are moistened, and the reward is assured.",
                hindi = "प्यास बुझ गई, नसें गीली हो गईं, और इनाम पक्का है।",
                urdu = "پیاسی بجھ گئی، رگیں گیلی ہو گئیں، اور اجر پکا ہے۔",
                malaysian = "Telah hilang dahaga, menjadi lembut urat-urat, dan pahala pasti."
            ),
            source = "Abu Dawood"
        ),

        DuaItem(
            id = "iftar_2",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Duʿāʾ at Iftar - 2",
                hindi = "इफ्तार की दुआ - 2",
                urdu = "افطار کی دعا - 2",
                malaysian = "Doa Berbuka Puasa - 2"
            ),
            arabic = "اللَّهُمَّ لَكَ صُمْتُ وَعَلَى رِزْقِكَ أَفْطَرْتُ",
            transliteration = "Allahumma inni laka sumtu wa ala rizq-ika-aftartu\n",
            translation = LocalizedDuaText(
                english = "O Allah! For You I have fasted and upon your provision, I have broken my fast.",
                hindi = "ऐ अल्लाह! मैंने आपके लिए रोज़ा रखा और आपकी रिज़क पर इफ्तार किया।",
                urdu = "اے اللہ! میں نے آپ کے لیے روزہ رکھا اور آپ کی رزق پر افطار کیا۔",
                malaysian = "Wahai Allah! Untuk-Mu aku berpuasa dan dengan rezeki-Mu, aku berbuka puasa."
            ),
            source = "Abu Dawood"
        ),

        DuaItem(
            id = "laylat_qadr",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Laylat al-Qadr",
                hindi = "लैलतुल कद्र",
                urdu = "لیلة القدر",
                malaysian = "Lailatul Qadar"
            ),
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka 'afuwwun tuḥibbul-'afwa fa'fu 'annī",
            translation = LocalizedDuaText(
                english = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
                hindi = "ऐ अल्लाह, आप बहुत क्षमाशील हैं और क्षमा करना पसंद करते हैं, तो मुझे क्षमा करें।",
                urdu = "اے اللہ، آپ بہت بخشش والے ہیں اور بخشش کرنا پسند کرتے ہیں، تو مجھے بخش دیں۔",
                malaysian = "Wahai Allah, Engkau Maha Pengampun, dan Engkau suka mengampuni, maka ampunilah aku."
            ),
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "first_ashra",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "First Ashra (Mercy)",
                hindi = "पहला अशरा (रहमत)",
                urdu = "پہلا عشرہ (رحمت)",
                malaysian = "Sepuluh Hari Pertama (Rahmat)"
            ),
            arabic = "يَا حَيُّ يَا قَيُّومُ بِرَحْمَتِكَ أَسْتَغيثَُْ",
            transliteration = "Ya Hayyu Ya Qayyum bi rehmatika astaghees",
            translation = LocalizedDuaText(
                english = "Oh Everliving, The Everlasting, I seek Your help through Your mercy.",
                hindi = "ऐ हे जीवनदाता, हे सदैव, मैं आपकी रहमत से दद मांगता हूँ।",
                urdu = "اے زندہ والا، اے ہمیشہ والا، میں آپ کی رحمت سے مدد مانگتا ہوں۔",
                malaysian = "Wahai Yang Hidup, Yang Kekal, aku memohon pertolongan-Mu melalui rahmat-Mu."
            ),
            source = "Traditional"
        ),

        DuaItem(
            id = "second_ashra",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Second Ashra (Forgiveness)",
                hindi = "दूसरा अशरा (क्षमा)",
                urdu = "دوسرا عشرہ (بخشش)",
                malaysian = "Sepuluh Hari Kedua (Keampunan)"
            ),
            arabic = "أَسْتَغْفِرُ اللّٰهَ رَبِّي مِنْ كُلِّ ذَنْبٍ",
            transliteration = "Astaghfirullāha rabbī min kulli dhamb",
            translation = LocalizedDuaText(
                english = "I seek forgiveness from Allah for every sin.",
                hindi = "मैं अल्लाह से हर गुनाह के लिए क्षमा मांगता हूँ।",
                urdu = "میں اللہ سے ہر گناہ کے لیے بخشش مانگتا ہوں۔",
                malaysian = "Aku memohon keampunan dari Allah untuk setiap dosa."
            ),
            source = "Traditional"
        ),

        DuaItem(
            id = "third_ashra",
            categoryId = "ramadan",
            title = LocalizedDuaText(
                english = "Third Ashra (Protection)",
                hindi = "तीसरा अशरा (सुरक्षा)",
                urdu = "تیسرا عشرہ (حفاظت)",
                malaysian = "Sepuluh Hari Ketiga (Perlindungan)"
            ),
            arabic = "اللَّهُمَّ أَجِرْنِي مِنَ النَّارِ",
            transliteration = "Allahumma ajirnī min an-nār",
            translation = LocalizedDuaText(
                english = "O Allah, protect me from the Fire.",
                hindi = "ऐ अल्लाह, मुझे आग से बचाएं।",
                urdu = "اے اللہ، مجھے آگ سے بچائیں۔",
                malaysian = "Wahai Allah, lindungilah aku dari api neraka."
            ),
            source = "Traditional"
        )
    )

    // Qur’an – Rabbana Duʿāʾs
    val quranDuas = listOf(

        DuaItem(
            id = "rabbana_1",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Accept From Us",
                hindi = "हमसे स्वीकार करें",
                urdu = "ہم سے قبول فرما",
                malaysian = "Terimalah Dari Kami"
            ),
            arabic = "رَبَّنَا تَقَبَّلْ مِنَّا",
            transliteration = "Rabbana taqabbal minnā",
            translation = LocalizedDuaText(
                english = "Our Lord, accept from us.",
                hindi = "हे हमारे पालनहार, हमसे स्वीकार करें।",
                urdu = "اے ہمارے رب، ہم سے قبول فرما۔",
                malaysian = "Wahai Tuhan kami, terimalah daripada kami."
            ),
            source = "Qur’an 2:127"
        ),

        DuaItem(
            id = "rabbana_2",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Guide Our Hearts",
                hindi = "हमारे दिलों को भटकने न दें",
                urdu = "ہمارے دلوں کو ٹیڑھا نہ ہونے دے",
                malaysian = "Jangan Palingkan Hati Kami"
            ),
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا",
            transliteration = "Rabbana lā tuzigh qulūbanā",
            translation = LocalizedDuaText(
                english = "Our Lord, do not let our hearts deviate.",
                hindi = "हे हमारे पालनहार, हमारे दिलों को भटकने न दें।",
                urdu = "اے ہمارے رب، ہمارے دلوں کو گمراہ نہ ہونے دے۔",
                malaysian = "Wahai Tuhan kami, janganlah Engkau palingkan hati kami."
            ),
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "rabbana_3",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Forgive Us",
                hindi = "हमें क्षमा करें",
                urdu = "ہمیں بخش دے",
                malaysian = "Ampunkan Kami"
            ),
            arabic = "رَبَّنَا اغْفِرْ لَنَا ذُنُوبَنَا",
            transliteration = "Rabbana ighfir lanā dhunūbanā",
            translation = LocalizedDuaText(
                english = "Our Lord, forgive us our sins.",
                hindi = "हे हमारे पालनहार, हमारे गुनाहों को क्षमा करें।",
                urdu = "اے ہمارے رب، ہمارے گناہوں کو معاف فرما۔",
                malaysian = "Wahai Tuhan kami, ampunkanlah dosa-dosa kami."
            ),
            source = "Qur’an 3:16"
        )
    )


    //  Daily Duʿāʾs
    val dailyDuas = listOf(

        DuaItem(
            id = "daily_1",
            categoryId = "daily",
            title = LocalizedDuaText(
                english = "Morning Protection",
                hindi = "सुबह की सुरक्षा",
                urdu = "صبح کی حفاظت",
                malaysian = "Perlindungan Pagi"
            ),
            arabic = "اللَّهُمَّ بِكَ أَصْبَحْنَا",
            transliteration = "Allahumma bika aṣbaḥnā",
            translation = LocalizedDuaText(
                english = "O Allah, by You we enter the morning.",
                hindi = "ऐ अल्लाह, आपकी मदद से हमने सुबह की शुरुआत की।",
                urdu = "اے اللہ، تیری مدد سے ہم نے صبح کی۔",
                malaysian = "Wahai Allah, dengan-Mu kami memasuki waktu pagi."
            ),
            source = "Abu Dawood"
        ),

        DuaItem(
            id = "daily_2",
            categoryId = "daily",
            title = LocalizedDuaText(
                english = "Before Sleep",
                hindi = "सोने से पहले",
                urdu = "سونے سے پہلے",
                malaysian = "Sebelum Tidur"
            ),
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            transliteration = "Bismika Allahumma amūtu wa aḥyā",
            translation = LocalizedDuaText(
                english = "In Your name, O Allah, I die and I live.",
                hindi = "ऐ अल्लाह, आपके नाम से मैं मरता हूँ और जीवित रहता हूँ।",
                urdu = "اے اللہ، تیرے نام سے میں مرتا اور جیتا ہوں۔",
                malaysian = "Dengan nama-Mu wahai Allah, aku mati dan aku hidup."
            ),
            source = "Bukhari"
        )
    )

    //  Qur’an – Rabbana Duʿāʾs (30)
    val quranRabbanaDuas = listOf(

        DuaItem(
            id = "rabbana_1",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Accept From Us",
                hindi = "हमसे स्वीकार करें",
                urdu = "ہم سے قبول فرما",
                malaysian = "Terimalah Dari Kami"
            ),
            arabic = "رَبَّنَا تَقَبَّلْ مِنَّا",
            transliteration = "Rabbana taqabbal minnā",
            translation = LocalizedDuaText(
                english = "Our Lord, accept from us.",
                hindi = "हे हमारे पालनहार, हमसे स्वीकार करें।",
                urdu = "اے ہمارے رب، ہم سے قبول فرما۔",
                malaysian = "Wahai Tuhan kami, terimalah daripada kami."
            ),
            source = "Qur’an 2:127"
        ),

        DuaItem(
            id = "rabbana_2",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Make Us Muslims",
                hindi = "हमें आज्ञाकारी मुसलमान बना",
                urdu = "ہمیں اپنا فرمانبردار مسلمان بنا",
                malaysian = "Jadikan Kami Muslim"
            ),
            arabic = "رَبَّنَا وَاجْعَلْنَا مُسْلِمَيْنِ لَكَ",
            transliteration = "Rabbana waj‘alnā muslimayni laka",
            translation = LocalizedDuaText(
                english = "Our Lord, make us Muslims in submission to You.",
                hindi = "हे हमारे पालनहार, हमें अपना आज्ञाकारी मुसलमान बना।",
                urdu = "اے ہمارے رب، ہمیں اپنا فرمانبردار مسلمان بنا۔",
                malaysian = "Wahai Tuhan kami, jadikanlah kami Muslim yang tunduk kepada-Mu."
            ),
            source = "Qur’an 2:128"
        ),

        DuaItem(
            id = "rabbana_3",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Forgive Us",
                hindi = "हमें क्षमा करें",
                urdu = "ہمیں بخش دے",
                malaysian = "Ampunkan Kami"
            ),
            arabic = "رَبَّنَا اغْفِرْ لَنَا ذُنُوبَنَا",
            transliteration = "Rabbana ighfir lanā dhunūbanā",
            translation = LocalizedDuaText(
                english = "Our Lord, forgive us our sins.",
                hindi = "हे हमारे पालनहार, हमारे गुनाहों को क्षमा करें।",
                urdu = "اے ہمارے رب، ہمارے گناہوں کو معاف فرما۔",
                malaysian = "Wahai Tuhan kami, ampunkanlah dosa-dosa kami."
            ),
            source = "Qur’an 3:16"
        ),

        DuaItem(
            id = "rabbana_4",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Do Not Let Hearts Deviate",
                hindi = "हमारे दिलों को भटकने न दें",
                urdu = "ہمارے دلوں کو ٹیڑھا نہ ہونے دے",
                malaysian = "Jangan Palingkan Hati Kami"
            ),
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا",
            transliteration = "Rabbana lā tuzigh qulūbanā",
            translation = LocalizedDuaText(
                english = "Our Lord, do not let our hearts deviate after You have guided us.",
                hindi = "हे हमारे पालनहार, मार्गदर्शन के बाद हमारे दिलों को भटकने न दें।",
                urdu = "اے ہمارے رب، ہدایت دینے کے بعد ہمارے دلوں کو ٹیڑھا نہ ہونے دے۔",
                malaysian = "Wahai Tuhan kami, jangan Engkau palingkan hati kami setelah Engkau memberi petunjuk."
            ),
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "rabbana_5",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Bestow Mercy",
                hindi = "हम पर दया करें",
                urdu = "ہم پر رحم فرما",
                malaysian = "Kurniakan Rahmat"
            ),
            arabic = "وَهَبْ لَنَا مِن لَّدُنكَ رَحْمَةً",
            transliteration = "Wahab lanā min ladunka raḥmah",
            translation = LocalizedDuaText(
                english = "Grant us mercy from Yourself.",
                hindi = "हमें अपनी ओर से दया प्रदान करें।",
                urdu = "ہمیں اپنی طرف سے رحمت عطا فرما۔",
                malaysian = "Kurniakanlah kepada kami rahmat daripada-Mu."
            ),
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "rabbana_6",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Remove Our Burdens",
                hindi = "हम पर बोझ न डालें",
                urdu = "ہم پر بوجھ نہ ڈال",
                malaysian = "Jangan Bebankan Kami"
            ),
            arabic = "رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَا إِصْرًا",
            transliteration = "Rabbana wa lā taḥmil ‘alaynā iṣran",
            translation = LocalizedDuaText(
                english = "Our Lord, do not place upon us a burden like those before us.",
                hindi = "हे हमारे पालनहार, हम पर पहले लोगों जैसा बोझ न डालें।",
                urdu = "اے ہمارے رب، ہم پر پہلے لوگوں جیسا بوجھ نہ ڈال۔",
                malaysian = "Wahai Tuhan kami, jangan Engkau bebankan kami seperti orang-orang sebelum kami."
            ),
            source = "Qur’an 2:286"
        ),

        DuaItem(
            id = "rabbana_7",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Pardon Us",
                hindi = "हमें क्षमा करें",
                urdu = "ہمیں معاف فرما",
                malaysian = "Maafkan Kami"
            ),
            arabic = "رَبَّنَا وَلَا تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا",
            transliteration = "Rabbana lā tu’ākhidhnā in nasīnā aw akhṭa’nā",
            translation = LocalizedDuaText(
                english = "Our Lord, do not punish us if we forget or make a mistake.",
                hindi = "हे हमारे पालनहार, यदि हम भूल जाएँ या गलती करें तो हमें न पकड़ें।",
                urdu = "اے ہمارے رب، اگر ہم بھول جائیں یا خطا کریں تو ہمیں نہ پکڑ۔",
                malaysian = "Wahai Tuhan kami, janganlah Engkau menghukum kami jika kami lupa atau tersalah."
            ),
            source = "Qur’an 2:286"
        ),

        DuaItem(
            id = "rabbana_8",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Grant Patience",
                hindi = "हमें धैर्य प्रदान करें",
                urdu = "ہمیں صبر عطا فرما",
                malaysian = "Kurniakan Kesabaran"
            ),
            arabic = "رَبَّنَا افْرِغْ عَلَيْنَا صَبْرًا",
            transliteration = "Rabbana afrigh ‘alaynā ṣabrā",
            translation = LocalizedDuaText(
                english = "Our Lord, pour upon us patience.",
                hindi = "हे हमारे पालनहार, हम पर धैर्य उंडेल दें।",
                urdu = "اے ہمارے رب، ہم پر صبر نازل فرما۔",
                malaysian = "Wahai Tuhan kami, limpahkanlah kesabaran ke atas kami."
            ),
            source = "Qur’an 2:250"
        ),

        DuaItem(
            id = "rabbana_9",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Do Not Destroy Us",
                hindi = "हमें नष्ट न करें",
                urdu = "ہمیں ہلاک نہ فرما",
                malaysian = "Jangan Binasakan Kami"
            ),
            arabic = "رَبَّنَا لَا تُهْلِكْنَا",
            transliteration = "Rabbana lā tuhliknā",
            translation = LocalizedDuaText(
                english = "Our Lord, do not destroy us.",
                hindi = "हे हमारे पालनहार, हमें नष्ट न करें।",
                urdu = "اے ہمارے رب، ہمیں ہلاک نہ فرما۔",
                malaysian = "Wahai Tuhan kami, janganlah Engkau binasakan kami."
            ),
            source = "Qur’an 7:155"
        ),

        DuaItem(
            id = "rabbana_10",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Forgive My Parents",
                hindi = "मेरे माता-पिता को क्षमा करें",
                urdu = "میرے والدین کو معاف فرما",
                malaysian = "Ampuni Ibu Bapa Saya"
            ),
            arabic = "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ",
            transliteration = "Rabbana ighfir lī wa liwālidayya",
            translation = LocalizedDuaText(
                english = "Our Lord, forgive me and my parents.",
                hindi = "हे हमारे पालनहार, मुझे और मेरे माता-पिता को क्षमा करें।",
                urdu = "اے ہمارے رب، مجھے اور میرے والدین کو معاف فرما۔",
                malaysian = "Wahai Tuhan kami, ampunkan aku dan ibu bapaku."
            ),
            source = "Qur’an 14:41"
        ),

        DuaItem(
            id = "rabbana_11",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Make Us Grateful",
                hindi = "हमें कृतज्ञ बना",
                urdu = "ہمیں شکر گزار بنا",
                malaysian = "Jadikan Kami Bersyukur"
            ),
            arabic = "رَبَّنَا أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ",
            transliteration = "Rabbana awzi‘nī an ashkura ni‘mataka",
            translation = LocalizedDuaText(
                english = "Our Lord, enable me to be grateful for Your favor.",
                hindi = "हे हमारे पालनहार, हमें अपनी नेमतों का शुक्र अदा करने की तौफ़ीक़ दे।",
                urdu = "اے ہمارے رب، ہمیں اپنی نعمت کا شکر ادا کرنے کی توفیق دے۔",
                malaysian = "Wahai Tuhan kami, bantulah kami untuk bersyukur atas nikmat-Mu."
            ),
            source = "Qur’an 27:19"
        ),

        DuaItem(
            id = "rabbana_12",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Good in This World",
                hindi = "दुनिया में भलाई",
                urdu = "دنیا کی بھلائی",
                malaysian = "Kebaikan di Dunia"
            ),
            arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً",
            transliteration = "Rabbana ātinā fid-dunyā ḥasanah",
            translation = LocalizedDuaText(
                english = "Our Lord, grant us good in this world.",
                hindi = "हे हमारे पालनहार, हमें दुनिया में भलाई प्रदान करें।",
                urdu = "اے ہمارے رب، ہمیں دنیا میں بھلائی عطا فرما۔",
                malaysian = "Wahai Tuhan kami, kurniakanlah kami kebaikan di dunia."
            ),
            source = "Qur’an 2:201"
        ),

        DuaItem(
            id = "rabbana_13",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Save From Hellfire",
                hindi = "नरक की आग से बचाएं",
                urdu = "جہنم کی آگ سے بچا",
                malaysian = "Selamatkan Dari Neraka"
            ),
            arabic = "وَقِنَا عَذَابَ النَّارِ",
            transliteration = "Wa qinā ‘adhāban-nār",
            translation = LocalizedDuaText(
                english = "And protect us from the punishment of the Fire.",
                hindi = "और हमें आग के अज़ाब से बचाएं।",
                urdu = "اور ہمیں جہنم کے عذاب سے بچا۔",
                malaysian = "Dan lindungilah kami daripada azab neraka."
            ),
            source = "Qur’an 2:201"
        ),

        DuaItem(
            id = "rabbana_14",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Perfect Our Light",
                hindi = "हमारा नूर पूरा करें",
                urdu = "ہمارا نور مکمل فرما",
                malaysian = "Sempurnakan Cahaya Kami"
            ),
            arabic = "رَبَّنَا أَتْمِمْ لَنَا نُورَنَا",
            transliteration = "Rabbana atmim lanā nūranā",
            translation = LocalizedDuaText(
                english = "Our Lord, perfect for us our light.",
                hindi = "हे हमारे पालनहार, हमारे लिए हमारा नूर पूरा करें।",
                urdu = "اے ہمارے رب، ہمارے لیے ہمارا نور مکمل فرما۔",
                malaysian = "Wahai Tuhan kami, sempurnakanlah cahaya kami."
            ),
            source = "Qur’an 66:8"
        ),

        DuaItem(
            id = "rabbana_15",
            categoryId = "quran",
            title = LocalizedDuaText(
                english = "Forgive the Believers",
                hindi = "ईमान वालों को क्षमा करें",
                urdu = "ایمان والوں کو بخش دے",
                malaysian = "Ampuni Orang Beriman"
            ),
            arabic = "رَبَّنَا اغْفِرْ لَنَا وَلِإِخْوَانِنَا",
            transliteration = "Rabbana ighfir lanā wa li-ikhwāninā",
            translation = LocalizedDuaText(
                english = "Our Lord, forgive us and our brothers in faith.",
                hindi = "हे हमारे पालनहार, हमें और हमारे ईमान वाले भाइयों को क्षमा करें।",
                urdu = "اے ہمارے رب، ہمیں اور ہمارے ایمان والے بھائیوں کو معاف فرما۔",
                malaysian = "Wahai Tuhan kami, ampunkanlah kami dan saudara-saudara kami yang beriman."
            ),
            source = "Qur’an 59:10"
        )
    )

    //
    val hisnulMuslimDuas = listOf(

        DuaItem(
            id = "hm_1",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Duʿāʾ Before Sleeping",
                hindi = "सोने से पहले की दुआ",
                urdu = "سونے سے پہلے کی دعا",
                malaysian = "Doa Sebelum Tidur"
            ),
            arabic = "بِسْمِكَ رَبِّي وَضَعْتُ جَنْبِي",
            transliteration = "Bismika rabbī waḍa‘tu janbī",
            translation = LocalizedDuaText(
                english = "In Your name my Lord, I lie down.",
                hindi = "हे मेरे रब, आपके नाम से मैं करवट लेता हूँ।",
                urdu = "اے میرے رب، تیرے نام سے میں لیٹتا ہوں۔",
                malaysian = "Dengan nama-Mu wahai Tuhanku, aku berbaring."
            ),
            source = "Bukhari, Muslim"
        ),

        DuaItem(
            id = "hm_2",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Duʿāʾ After Waking Up",
                hindi = "जागने के बाद की दुआ",
                urdu = "جاگنے کے بعد کی دعا",
                malaysian = "Doa Selepas Bangun Tidur"
            ),
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا",
            transliteration = "Alhamdu lillāhil-ladhī aḥyānā",
            translation = LocalizedDuaText(
                english = "All praise is for Allah who gave us life after death.",
                hindi = "सारी प्रशंसा अल्लाह के लिए है जिसने हमें मृत्यु के बाद जीवन दिया।",
                urdu = "تمام تعریفیں اللہ کے لیے ہیں جس نے ہمیں موت کے بعد زندگی دی۔",
                malaysian = "Segala puji bagi Allah yang menghidupkan kami selepas mati."
            ),
            source = "Bukhari"
        ),

        DuaItem(
            id = "hm_3",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Entering the Bathroom",
                hindi = "बाथरूम में प्रवेश की दुआ",
                urdu = "بیت الخلاء میں داخل ہونے کی دعا",
                malaysian = "Doa Masuk Tandas"
            ),
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ",
            transliteration = "Allahumma innī a‘ūdhu bika minal-khubuthi",
            translation = LocalizedDuaText(
                english = "O Allah, I seek refuge in You from evil and evil-doers.",
                hindi = "ऐ अल्लाह, मैं आपसे बुराई और बुरे जिन्नों से पनाह मांगता हूँ।",
                urdu = "اے اللہ، میں تیری پناہ مانگتا ہوں ناپاکی اور ناپاک جنوں سے۔",
                malaysian = "Wahai Allah, aku berlindung kepada-Mu daripada kejahatan."
            ),
            source = "Bukhari, Muslim"
        ),

        DuaItem(
            id = "hm_4",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Leaving the Bathroom",
                hindi = "बाथरूम से निकलने की दुआ",
                urdu = "بیت الخلاء سے نکلنے کی دعا",
                malaysian = "Doa Keluar Tandas"
            ),
            arabic = "غُفْرَانَكَ",
            transliteration = "Ghufrānaka",
            translation = LocalizedDuaText(
                english = "I ask You for forgiveness.",
                hindi = "मैं आपसे क्षमा मांगता हूँ।",
                urdu = "میں تجھ سے بخشش مانگتا ہوں۔",
                malaysian = "Aku memohon keampunan-Mu."
            ),
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "hm_5",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Before Eating",
                hindi = "खाने से पहले",
                urdu = "کھانے سے پہلے",
                malaysian = "Sebelum Makan"
            ),
            arabic = "بِسْمِ اللَّهِ",
            transliteration = "Bismillāh",
            translation = LocalizedDuaText(
                english = "In the name of Allah.",
                hindi = "अल्लाह के नाम से।",
                urdu = "اللہ کے نام سے۔",
                malaysian = "Dengan nama Allah."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "hm_6",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "After Eating",
                hindi = "खाने के बाद",
                urdu = "کھانے کے بعد",
                malaysian = "Selepas Makan"
            ),
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا",
            transliteration = "Alhamdu lillāhil-ladhī aṭ‘amanā",
            translation = LocalizedDuaText(
                english = "All praise is for Allah who fed us.",
                hindi = "सारी प्रशंसा अल्लाह के लिए है जिसने हमें भोजन दिया।",
                urdu = "تمام تعریفیں اللہ کے لیے ہیں جس نے ہمیں کھانا دیا۔",
                malaysian = "Segala puji bagi Allah yang memberi kami rezeki."
            ),
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "hm_7",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Entering the Home",
                hindi = "घर में प्रवेश की दुआ",
                urdu = "گھر میں داخل ہونے کی دعا",
                malaysian = "Doa Masuk Rumah"
            ),
            arabic = "بِسْمِ اللَّهِ وَلَجْنَا",
            transliteration = "Bismillāhi walajnā",
            translation = LocalizedDuaText(
                english = "In the name of Allah we enter.",
                hindi = "अल्लाह के नाम से हम घर में प्रवेश करते हैं।",
                urdu = "اللہ کے نام سے ہم داخل ہوتے ہیں۔",
                malaysian = "Dengan nama Allah kami masuk."
            ),
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "hm_8",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Leaving the Home",
                hindi = "घर से निकलने की दुआ",
                urdu = "گھر سے نکلنے کی دعا",
                malaysian = "Doa Keluar Rumah"
            ),
            arabic = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ",
            transliteration = "Bismillāhi tawakkaltu ‘alallāh",
            translation = LocalizedDuaText(
                english = "In the name of Allah, I place my trust in Allah.",
                hindi = "अल्लाह के नाम से, मैं अल्लाह पर भरोसा करता हूँ।",
                urdu = "اللہ کے نام سے، میں اللہ پر بھروسا کرتا ہوں۔",
                malaysian = "Dengan nama Allah, aku bertawakal kepada Allah."
            ),
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "hm_9",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "Protection From Harm",
                hindi = "हानि से सुरक्षा",
                urdu = "نقصان سے حفاظت",
                malaysian = "Perlindungan Dari Bahaya"
            ),
            arabic = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ",
            transliteration = "A‘ūdhu bikalimātillāhit-tāmmāt",
            translation = LocalizedDuaText(
                english = "I seek refuge in the perfect words of Allah.",
                hindi = "मैं अल्लाह के पूर्ण शब्दों की पनाह मांगता हूँ।",
                urdu = "میں اللہ کے کامل کلمات کی پناہ مانگتا ہوں۔",
                malaysian = "Aku berlindung dengan kalimah Allah yang sempurna."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "hm_10",
            categoryId = "hisnul",
            title = LocalizedDuaText(
                english = "When Anxious or Distressed",
                hindi = "घबराहट या परेशानी में",
                urdu = "گھبراہٹ یا پریشانی میں",
                malaysian = "Ketika Cemas atau Tertekan"
            ),
            arabic = "اللَّهُمَّ إِنِّي عَبْدُكَ",
            transliteration = "Allahumma innī ‘abduka",
            translation = LocalizedDuaText(
                english = "O Allah, I am Your servant.",
                hindi = "ऐ अल्लाह, मैं आपका बंदा हूँ।",
                urdu = "اے اللہ، میں تیرا بندہ ہوں۔",
                malaysian = "Wahai Allah, sesungguhnya aku adalah hamba-Mu."
            ),
            source = "Ahmad"
        )
    )

    //  Duʿāʾs of the Prophets
    val prophetDuas = listOf(

        DuaItem(
            id = "prop_adam",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Adam (AS)",
                hindi = "नबी आदम (अ.स.) की दुआ",
                urdu = "حضرت آدمؑ کی دعا",
                malaysian = "Doa Nabi Adam (AS)"
            ),
            arabic = "رَبَّنَا ظَلَمْنَا أَنفُسَنَا",
            transliteration = "Rabbana ẓalamnā anfusanā",
            translation = LocalizedDuaText(
                english = "Our Lord, we have wronged ourselves.",
                hindi = "हे हमारे पालनहार, हमने अपने ऊपर ज़ुल्म किया है।",
                urdu = "اے ہمارے رب، ہم نے اپنے اوپر ظلم کیا ہے۔",
                malaysian = "Wahai Tuhan kami, kami telah menzalimi diri kami."
            ),
            source = "Qur’an 7:23"
        ),

        DuaItem(
            id = "prop_nuh",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Nūḥ (AS)",
                hindi = "नबी नूह (अ.स.) की दुआ",
                urdu = "حضرت نوحؑ کی دعا",
                malaysian = "Doa Nabi Nuh (AS)"
            ),
            arabic = "رَبِّ إِنِّي مَغْلُوبٌ فَانتَصِرْ",
            transliteration = "Rabbi innī maghlūbun fa-ntaṣir",
            translation = LocalizedDuaText(
                english = "My Lord, I am overpowered, so help me.",
                hindi = "हे मेरे पालनहार, मैं पराजित हो गया हूँ, मेरी सहायता करें।",
                urdu = "اے میرے رب، میں مغلوب ہو چکا ہوں، میری مدد فرما۔",
                malaysian = "Wahai Tuhanku, sesungguhnya aku telah dikalahkan, maka tolonglah aku."
            ),
            source = "Qur’an 54:10"
        ),

        DuaItem(
            id = "prop_ibrahim_1",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Ibrāhīm (AS)",
                hindi = "नबी इब्राहीम (अ.स.) की दुआ",
                urdu = "حضرت ابراہیمؑ کی دعا",
                malaysian = "Doa Nabi Ibrahim (AS)"
            ),
            arabic = "رَبِّ اجْعَلْنِي مُقِيمَ الصَّلَاةِ",
            transliteration = "Rabbi ij‘alnī muqīmaṣ-ṣalāh",
            translation = LocalizedDuaText(
                english = "My Lord, make me an establisher of prayer.",
                hindi = "हे मेरे पालनहार, मुझे नमाज़ क़ायम करने वाला बना।",
                urdu = "اے میرے رب، مجھے نماز قائم کرنے والا بنا۔",
                malaysian = "Wahai Tuhanku, jadikanlah aku orang yang mendirikan solat."
            ),
            source = "Qur’an 14:40"
        ),

        DuaItem(
            id = "prop_ibrahim_2",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ for a Righteous Offspring",
                hindi = "नेक संतान के लिए दुआ",
                urdu = "نیک اولاد کے لیے دعا",
                malaysian = "Doa Memohon Zuriat Soleh"
            ),
            arabic = "رَبِّ هَبْ لِي مِنَ الصَّالِحِينَ",
            transliteration = "Rabbi hab lī minaṣ-ṣāliḥīn",
            translation = LocalizedDuaText(
                english = "My Lord, grant me righteous offspring.",
                hindi = "हे मेरे पालनहार, मुझे नेक संतान प्रदान करें।",
                urdu = "اے میرے رب، مجھے نیک اولاد عطا فرما۔",
                malaysian = "Wahai Tuhanku, kurniakanlah aku zuriat yang soleh."
            ),
            source = "Qur’an 37:100"
        ),

        DuaItem(
            id = "prop_musa",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Mūsā (AS)",
                hindi = "नबी मूसा (अ.स.) की दुआ",
                urdu = "حضرت موسیٰؑ کی دعا",
                malaysian = "Doa Nabi Musa (AS)"
            ),
            arabic = "رَبِّ اشْرَحْ لِي صَدْرِي",
            transliteration = "Rabbi ishraḥ lī ṣadrī",
            translation = LocalizedDuaText(
                english = "My Lord, expand my chest.",
                hindi = "हे मेरे पालनहार, मेरा सीना खोल दें।",
                urdu = "اے میرے رب، میرا سینہ کھول دے۔",
                malaysian = "Wahai Tuhanku, lapangkanlah dadaku."
            ),
            source = "Qur’an 20:25–28"
        ),

        DuaItem(
            id = "prop_ayyub",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Ayyūb (AS)",
                hindi = "नबी अय्यूब (अ.स.) की दुआ",
                urdu = "حضرت ایوبؑ کی دعا",
                malaysian = "Doa Nabi Ayyub (AS)"
            ),
            arabic = "أَنِّي مَسَّنِيَ الضُّرُّ وَأَنتَ أَرْحَمُ الرَّاحِمِينَ",
            transliteration = "Annī massaniyaḍ-ḍurru wa anta arḥamur-rāḥimīn",
            translation = LocalizedDuaText(
                english = "Indeed, adversity has touched me, and You are the Most Merciful.",
                hindi = "निश्चय ही मुझे कष्ट पहुँचा है, और आप सबसे अधिक दयालु हैं।",
                urdu = "یقیناً مجھے تکلیف پہنچی ہے اور تو سب سے زیادہ رحم کرنے والا ہے۔",
                malaysian = "Sesungguhnya aku ditimpa kesusahan dan Engkau Maha Penyayang."
            ),
            source = "Qur’an 21:83"
        ),

        DuaItem(
            id = "prop_yunus",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Yūnus (AS)",
                hindi = "नबी यूनुस (अ.स.) की दुआ",
                urdu = "حضرت یونسؑ کی دعا",
                malaysian = "Doa Nabi Yunus (AS)"
            ),
            arabic = "لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ",
            transliteration = "Lā ilāha illā anta subḥānaka",
            translation = LocalizedDuaText(
                english = "There is no deity except You; exalted are You.",
                hindi = "तेरे सिवा कोई पूज्य नहीं, तू पवित्र है।",
                urdu = "تیرے سوا کوئی معبود نہیں، تو پاک ہے۔",
                malaysian = "Tiada Tuhan selain Engkau, Maha Suci Engkau."
            ),
            source = "Qur’an 21:87"
        ),

        DuaItem(
            id = "prop_zakariya",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Zakariyyā (AS)",
                hindi = "नबी ज़करिया (अ.स.) की दुआ",
                urdu = "حضرت زکریاؑ کی دعا",
                malaysian = "Doa Nabi Zakariya (AS)"
            ),
            arabic = "رَبِّ لَا تَذَرْنِي فَرْدًا",
            transliteration = "Rabbi lā tadharnī fardan",
            translation = LocalizedDuaText(
                english = "My Lord, do not leave me alone.",
                hindi = "हे मेरे पालनहार, मुझे अकेला न छोड़ें।",
                urdu = "اے میرے رب، مجھے اکیلا نہ چھوڑ۔",
                malaysian = "Wahai Tuhanku, janganlah Engkau biarkan aku bersendirian."
            ),
            source = "Qur’an 21:89"
        ),

        DuaItem(
            id = "prop_yusuf",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Yūsuf (AS)",
                hindi = "नबी यूसुफ़ (अ.स.) की दुआ",
                urdu = "حضرت یوسفؑ کی دعا",
                malaysian = "Doa Nabi Yusuf (AS)"
            ),
            arabic = "تَوَفَّنِي مُسْلِمًا وَأَلْحِقْنِي بِالصَّالِحِينَ",
            transliteration = "Tawaffanī musliman wa alḥiqnī biṣ-ṣāliḥīn",
            translation = LocalizedDuaText(
                english = "Cause me to die as a Muslim and join me with the righteous.",
                hindi = "मुझे मुसलमान की अवस्था में मृत्यु दे और मुझे नेक लोगों में शामिल कर।",
                urdu = "مجھے مسلمان حالت میں وفات دے اور مجھے نیک لوگوں میں شامل فرما۔",
                malaysian = "Matikanlah aku sebagai seorang Muslim dan himpunkan aku bersama orang soleh."
            ),
            source = "Qur’an 12:101"
        ),

        DuaItem(
            id = "prop_sulayman",
            categoryId = "prophets",
            title = LocalizedDuaText(
                english = "Duʿāʾ of Prophet Sulaymān (AS)",
                hindi = "नबी सुलेमान (अ.स.) की दुआ",
                urdu = "حضرت سلیمانؑ کی دعا",
                malaysian = "Doa Nabi Sulaiman (AS)"
            ),
            arabic = "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ",
            transliteration = "Rabbi awzi‘nī an ashkura ni‘mataka",
            translation = LocalizedDuaText(
                english = "My Lord, enable me to be grateful for Your favor.",
                hindi = "हे मेरे पालनहार, मुझे अपनी नेमतों का शुक्र अदा करने की तौफ़ीक़ दे।",
                urdu = "اے میرے رب، مجھے اپنی نعمت کا شکر ادا کرنے کی توفیق دے۔",
                malaysian = "Wahai Tuhanku, bantulah aku untuk bersyukur atas nikmat-Mu."
            ),
            source = "Qur’an 27:19"
        )
    )

    //  Morning & Evening Adhkār
    val morningEveningAdhkar = listOf(

        DuaItem(
            id = "adhkar_ayah_kursi",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Āyat al-Kursī",
                hindi = "आयतुल कुर्सी",
                urdu = "آیت الکرسی",
                malaysian = "Ayat Kursi"
            ),
            arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",
            transliteration = "Allāhu lā ilāha illā Huwa al-Ḥayyul-Qayyūm",
            translation = LocalizedDuaText(
                english = "Allah—there is no deity except Him, the Ever-Living, the Sustainer.",
                hindi = "अल्लाह के सिवा कोई पूज्य नहीं, वही सदा जीवित और सबको संभालने वाला है।",
                urdu = "اللہ کے سوا کوئی معبود نہیں، وہی زندہ اور سب کو قائم رکھنے والا ہے۔",
                malaysian = "Allah, tiada Tuhan selain Dia, Yang Maha Hidup dan Maha Berdiri Sendiri."
            ),
            source = "Qur’an 2:255"
        ),

        DuaItem(
            id = "adhkar_three_quls",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "The Three Quls",
                hindi = "तीन क़ुल",
                urdu = "تین قل",
                malaysian = "Tiga Qul"
            ),
            arabic = "قُلْ هُوَ اللَّهُ أَحَدٌ...",
            transliteration = "Qul huwa Allāhu aḥad…",
            translation = LocalizedDuaText(
                english = "Say: He is Allah, the One…",
                hindi = "कहिए: वह अल्लाह एक है…",
                urdu = "کہہ دیجیے: وہ اللہ ایک ہے…",
                malaysian = "Katakanlah: Dialah Allah Yang Maha Esa…"
            ),
            source = "Bukhari & Muslim"
        ),

        DuaItem(
            id = "adhkar_bismillah",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Protection from Harm",
                hindi = "हानि से सुरक्षा",
                urdu = "نقصان سے حفاظت",
                malaysian = "Perlindungan Dari Bahaya"
            ),
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ",
            transliteration = "Bismillāhilladhī lā yaḍurru ma‘asmihī shay’",
            translation = LocalizedDuaText(
                english = "In the name of Allah, with whose name nothing can harm.",
                hindi = "अल्लाह के नाम से, जिसके नाम के साथ कोई चीज़ नुकसान नहीं पहुँचा सकती।",
                urdu = "اللہ کے نام سے، جس کے نام کے ساتھ کوئی چیز نقصان نہیں پہنچا سکتی۔",
                malaysian = "Dengan nama Allah yang dengan nama-Nya tiada sesuatu pun dapat memudaratkan."
            ),
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "adhkar_rabb_allah",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Contentment with Allah",
                hindi = "अल्लाह पर संतोष",
                urdu = "اللہ پر راضی ہونا",
                malaysian = "Redha Dengan Allah"
            ),
            arabic = "رَضِيتُ بِاللَّهِ رَبًّا",
            transliteration = "Raḍītu billāhi rabbā",
            translation = LocalizedDuaText(
                english = "I am pleased with Allah as my Lord.",
                hindi = "मैं अल्लाह को अपना पालनहार मानकर संतुष्ट हूँ।",
                urdu = "میں اللہ کو اپنا رب مان کر راضی ہوں۔",
                malaysian = "Aku reda dengan Allah sebagai Tuhanku."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_hasbi_allah",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Allah is Sufficient",
                hindi = "अल्लाह ही काफ़ी है",
                urdu = "اللہ ہی کافی ہے",
                malaysian = "Allah Maha Mencukupi"
            ),
            arabic = "حَسْبِيَ اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ",
            transliteration = "Ḥasbiyallāhu lā ilāha illā Huwa",
            translation = LocalizedDuaText(
                english = "Allah is sufficient for me; there is no deity except Him.",
                hindi = "अल्लाह मेरे लिए काफ़ी है, उसके सिवा कोई पूज्य नहीं।",
                urdu = "اللہ میرے لیے کافی ہے، اس کے سوا کوئی معبود نہیں۔",
                malaysian = "Cukuplah Allah bagiku, tiada Tuhan selain Dia."
            ),
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "adhkar_astaghfirullah",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Seeking Forgiveness",
                hindi = "क्षमा की प्रार्थना",
                urdu = "بخشش طلب کرنا",
                malaysian = "Memohon Keampunan"
            ),
            arabic = "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ",
            transliteration = "Astaghfirullāha wa atūbu ilayh",
            translation = LocalizedDuaText(
                english = "I seek forgiveness from Allah and repent to Him.",
                hindi = "मैं अल्लाह से क्षमा मांगता हूँ और उसी की ओर तौबा करता हूँ।",
                urdu = "میں اللہ سے بخشش مانگتا ہوں اور اسی کی طرف توبہ کرتا ہوں۔",
                malaysian = "Aku memohon keampunan daripada Allah dan bertaubat kepada-Nya."
            ),
            source = "Bukhari"
        ),

        DuaItem(
            id = "adhkar_subhanallah",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Tasbīḥ, Taḥmīd, Takbīr",
                hindi = "तस्बीह और हम्द",
                urdu = "تسبیح اور حمد",
                malaysian = "Tasbih dan Pujian"
            ),
            arabic = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
            transliteration = "Subḥānallāhi wa biḥamdih",
            translation = LocalizedDuaText(
                english = "Glory and praise be to Allah.",
                hindi = "अल्लाह की पवित्रता और प्रशंसा है।",
                urdu = "اللہ پاک ہے اور اسی کی تعریف ہے۔",
                malaysian = "Maha Suci Allah dan segala puji bagi-Nya."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_dua_light",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Duʿāʾ for Light",
                hindi = "नूर की दुआ",
                urdu = "نور کی دعا",
                malaysian = "Doa Memohon Cahaya"
            ),
            arabic = "اللَّهُمَّ اجْعَلْ فِي قَلْبِي نُورًا",
            transliteration = "Allāhumma ij‘al fī qalbī nūrā",
            translation = LocalizedDuaText(
                english = "O Allah, place light in my heart.",
                hindi = "ऐ अल्लाह, मेरे दिल में नूर रख दे।",
                urdu = "اے اللہ، میرے دل میں نور رکھ دے۔",
                malaysian = "Wahai Allah, jadikanlah cahaya di dalam hatiku."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_evening_protection",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Evening Protection",
                hindi = "शाम की सुरक्षा",
                urdu = "شام کی حفاظت",
                malaysian = "Perlindungan Petang"
            ),
            arabic = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ",
            transliteration = "Amsaynā wa amsal-mulku lillāh",
            translation = LocalizedDuaText(
                english = "We have entered the evening and dominion belongs to Allah.",
                hindi = "हमने शाम में प्रवेश किया और सारा राज्य अल्लाह का है।",
                urdu = "ہم نے شام میں داخل کیا اور بادشاہت اللہ کی ہے۔",
                malaysian = "Kami memasuki waktu petang dan kekuasaan milik Allah."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_morning_dua",
            categoryId = "adhkar",
            title = LocalizedDuaText(
                english = "Morning Remembrance",
                hindi = "सुबह की ज़िक्र",
                urdu = "صبح کی یاد",
                malaysian = "Zikir Pagi"
            ),
            arabic = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ",
            transliteration = "Aṣbaḥnā wa aṣbaḥal-mulku lillāh",
            translation = LocalizedDuaText(
                english = "We have entered the morning and dominion belongs to Allah.",
                hindi = "हमने सुबह में प्रवेश किया और सारा राज्य अल्लाह का है।",
                urdu = "ہم نے صبح میں داخل کیا اور بادشاہت اللہ کی ہے۔",
                malaysian = "Kami memasuki waktu pagi dan kekuasaan milik Allah."
            ),
            source = "Muslim"
        )
    )

    //  Faith & Guidance Duʿāʾs
    val faithGuidanceDuas = listOf(

        DuaItem(
            id = "faith_1",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Guidance After Belief",
                hindi = "ईमान के बाद मार्गदर्शन",
                urdu = "ایمان کے بعد ہدایت",
                malaysian = "Petunjuk Selepas Iman"
            ),
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا بَعْدَ إِذْ هَدَيْتَنَا",
            transliteration = "Rabbana lā tuzigh qulūbanā ba‘da idh hadaytanā",
            translation = LocalizedDuaText(
                english = "Our Lord, do not let our hearts deviate after You have guided us.",
                hindi = "हे हमारे पालनहार, मार्गदर्शन देने के बाद हमारे दिलों को भटकने न दें।",
                urdu = "اے ہمارے رب، ہدایت دینے کے بعد ہمارے دلوں کو ٹیڑھا نہ ہونے دے۔",
                malaysian = "Wahai Tuhan kami, jangan Engkau palingkan hati kami setelah Engkau memberi petunjuk."
            ),
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "faith_2",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Increase in Knowledge",
                hindi = "ज्ञान में वृद्धि",
                urdu = "علم میں اضافہ",
                malaysian = "Tambahkan Ilmu"
            ),
            arabic = "رَبِّ زِدْنِي عِلْمًا",
            transliteration = "Rabbi zidnī ‘ilmā",
            translation = LocalizedDuaText(
                english = "My Lord, increase me in knowledge.",
                hindi = "हे मेरे पालनहार, मेरे ज्ञान में वृद्धि करें।",
                urdu = "اے میرے رب، میرے علم میں اضافہ فرما۔",
                malaysian = "Wahai Tuhanku, tambahkanlah aku ilmu."
            ),
            source = "Qur’an 20:114"
        ),

        DuaItem(
            id = "faith_3",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Firmness of Heart",
                hindi = "दिल की दृढ़ता",
                urdu = "دل کی ثابت قدمی",
                malaysian = "Keteguhan Hati"
            ),
            arabic = "يَا مُقَلِّبَ الْقُلُوبِ ثَبِّتْ قَلْبِي عَلَى دِينِكَ",
            transliteration = "Yā Muqallibal qulūb, thabbit qalbī ‘alā dīnik",
            translation = LocalizedDuaText(
                english = "O Turner of hearts, keep my heart firm upon Your religion.",
                hindi = "ऐ दिलों को फेरने वाले, मेरे दिल को अपने धर्म पर स्थिर रख।",
                urdu = "اے دلوں کو پھیرنے والے، میرے دل کو اپنے دین پر ثابت رکھ۔",
                malaysian = "Wahai Yang Membolak-balikkan hati, tetapkanlah hatiku di atas agama-Mu."
            ),
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "faith_4",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Righteous Guidance",
                hindi = "सही मार्गदर्शन",
                urdu = "درست رہنمائی",
                malaysian = "Petunjuk Yang Benar"
            ),
            arabic = "اللَّهُمَّ اهْدِنِي وَسَدِّدْنِي",
            transliteration = "Allāhumma ihdinī wa saddidnī",
            translation = LocalizedDuaText(
                english = "O Allah, guide me and keep me firm.",
                hindi = "ऐ अल्लाह, मुझे मार्ग दिखाएं और मुझे स्थिर रखें।",
                urdu = "اے اللہ، مجھے ہدایت دے اور مجھے ثابت قدم رکھ۔",
                malaysian = "Wahai Allah, berilah aku petunjuk dan keteguhan."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "faith_5",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Light in the Heart",
                hindi = "दिल में नूर",
                urdu = "دل میں نور",
                malaysian = "Cahaya di Hati"
            ),
            arabic = "اللَّهُمَّ اجْعَلْ فِي قَلْبِي نُورًا",
            transliteration = "Allāhumma ij‘al fī qalbī nūrā",
            translation = LocalizedDuaText(
                english = "O Allah, place light in my heart.",
                hindi = "ऐ अल्लाह, मेरे दिल में नूर रख दे।",
                urdu = "اے اللہ، میرے دل میں نور رکھ دے۔",
                malaysian = "Wahai Allah, jadikanlah cahaya di dalam hatiku."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "faith_6",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Guidance & Piety",
                hindi = "हिदायत और परहेज़गारी",
                urdu = "ہدایت اور تقویٰ",
                malaysian = "Petunjuk & Ketakwaan"
            ),
            arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْهُدَى وَالتُّقَى",
            transliteration = "Allāhumma innī as’alukal-hudā wat-tuqā",
            translation = LocalizedDuaText(
                english = "O Allah, I ask You for guidance and piety.",
                hindi = "ऐ अल्लाह, मैं आपसे हिदायत और परहेज़गारी मांगता हूँ।",
                urdu = "اے اللہ، میں تجھ سے ہدایت اور تقویٰ مانگتا ہوں۔",
                malaysian = "Wahai Allah, aku memohon petunjuk dan ketakwaan daripada-Mu."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "faith_7",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "True Faith",
                hindi = "सच्चा ईमान",
                urdu = "سچا ایمان",
                malaysian = "Iman Yang Sejati"
            ),
            arabic = "اللَّهُمَّ حَبِّبْ إِلَيْنَا الْإِيمَانَ",
            transliteration = "Allāhumma ḥabbib ilaynā al-īmān",
            translation = LocalizedDuaText(
                english = "O Allah, make faith beloved to us.",
                hindi = "ऐ अल्लाह, ईमान को हमें प्रिय बना दे।",
                urdu = "اے اللہ، ایمان کو ہمیں محبوب بنا دے۔",
                malaysian = "Wahai Allah, jadikanlah iman itu dicintai oleh kami."
            ),
            source = "Qur’an 49:7"
        ),

        DuaItem(
            id = "faith_8",
            categoryId = "faith",
            title = LocalizedDuaText(
                english = "Protection from Misguidance",
                hindi = "गुमराही से सुरक्षा",
                urdu = "گمراہی سے حفاظت",
                malaysian = "Perlindungan Dari Kesesatan"
            ),
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الضَّلَالِ",
            transliteration = "Allāhumma innī a‘ūdhu bika min aḍ-ḍalāl",
            translation = LocalizedDuaText(
                english = "O Allah, I seek refuge in You from misguidance.",
                hindi = "ऐ अल्लाह, मैं गुमराही से आपकी पनाह चाहता हूँ।",
                urdu = "اے اللہ، میں گمراہی سے تیری پناہ مانگتا ہوں۔",
                malaysian = "Wahai Allah, aku berlindung kepada-Mu daripada kesesatan."
            ),
            source = "Abu Dawud"
        )
    )

    //  Family & Children Duʿāʾs
    val familyDuas = listOf(

        DuaItem(
            id = "family_1",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Righteous Spouse & Children",
                hindi = "नेक जीवनसाथी और संतान",
                urdu = "نیک شریکِ حیات اور اولاد",
                malaysian = "Pasangan & Anak Yang Soleh"
            ),
            arabic = "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا وَذُرِّيَّاتِنَا قُرَّةَ أَعْيُنٍ",
            transliteration = "Rabbana hab lanā min azwājinā wa dhurriyyātinā qurrata a‘yun",
            translation = LocalizedDuaText(
                english = "Our Lord, grant us from our spouses and offspring comfort to our eyes.",
                hindi = "हे हमारे पालनहार, हमें हमारे जीवनसाथी और संतान से आँखों की ठंडक अता कर।",
                urdu = "اے ہمارے رب، ہمیں ہماری بیویوں اور اولاد سے آنکھوں کی ٹھنڈک عطا فرما۔",
                malaysian = "Wahai Tuhan kami, kurniakanlah kami pasangan dan zuriat sebagai penyejuk mata."
            ),
            source = "Qur’an 25:74"
        ),

        DuaItem(
            id = "family_2",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Protection for Children",
                hindi = "बच्चों की सुरक्षा",
                urdu = "بچوں کی حفاظت",
                malaysian = "Perlindungan Untuk Anak-anak"
            ),
            arabic = "أُعِيذُكُمَا بِكَلِمَاتِ اللَّهِ التَّامَّةِ مِنْ كُلِّ شَيْطَانٍ وَهَامَّةٍ",
            transliteration = "U‘īdhukumā bi kalimātillāhit-tāmmati min kulli shayṭānin wa hāmmah",
            translation = LocalizedDuaText(
                english = "I seek protection for you in the perfect words of Allah from every devil and harmful thing.",
                hindi = "मैं तुम्हें अल्लाह के पूर्ण शब्दों की पनाह में देता हूँ हर शैतान और नुकसान से।",
                urdu = "میں تمہیں اللہ کے کامل کلمات کی پناہ دیتا ہوں ہر شیطان اور نقصان سے۔",
                malaysian = "Aku memohon perlindungan untuk kamu dengan kalimah Allah yang sempurna daripada segala kejahatan."
            ),
            source = "Bukhari"
        ),

        DuaItem(
            id = "family_3",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Righteous Offspring",
                hindi = "नेक संतान",
                urdu = "نیک اولاد",
                malaysian = "Zuriat Yang Soleh"
            ),
            arabic = "رَبِّ هَبْ لِي مِنَ الصَّالِحِينَ",
            transliteration = "Rabbi hab lī minaṣ-ṣāliḥīn",
            translation = LocalizedDuaText(
                english = "My Lord, grant me righteous offspring.",
                hindi = "हे मेरे पालनहार, मुझे नेक संतान प्रदान करें।",
                urdu = "اے میرے رب، مجھے نیک اولاد عطا فرما۔",
                malaysian = "Wahai Tuhanku, kurniakanlah aku zuriat yang soleh."
            ),
            source = "Qur’an 37:100"
        ),

        DuaItem(
            id = "family_4",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Guidance for Family",
                hindi = "परिवार के लिए मार्गदर्शन",
                urdu = "خاندان کے لیے ہدایت",
                malaysian = "Petunjuk Untuk Keluarga"
            ),
            arabic = "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ",
            transliteration = "Rabbana-ghfir lī wa li wālidayya wa lil-mu’minīn",
            translation = LocalizedDuaText(
                english = "Our Lord, forgive me, my parents, and the believers.",
                hindi = "हे हमारे पालनहार, मुझे, मेरे माता-पिता और सभी मोमिनों को क्षमा करें।",
                urdu = "اے ہمارے رب، مجھے، میرے والدین اور تمام مومنوں کو بخش دے۔",
                malaysian = "Wahai Tuhan kami, ampunilah aku, ibu bapaku dan semua orang beriman."
            ),
            source = "Qur’an 14:41"
        ),

        DuaItem(
            id = "family_5",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Barakah in Family",
                hindi = "परिवार में बरकत",
                urdu = "خاندان میں برکت",
                malaysian = "Keberkatan Dalam Keluarga"
            ),
            arabic = "اللَّهُمَّ بَارِكْ لَنَا فِي أَهْلِنَا وَأَوْلَادِنَا",
            transliteration = "Allāhumma bārik lanā fī ahlinā wa awlādina",
            translation = LocalizedDuaText(
                english = "O Allah, bless our families and our children.",
                hindi = "ऐ अल्लाह, हमारे परिवार और हमारी संतान में बरकत दे।",
                urdu = "اے اللہ، ہمارے خاندان اور ہماری اولاد میں برکت عطا فرما۔",
                malaysian = "Wahai Allah, berkatilah keluarga dan anak-anak kami."
            ),
            source = "Reported Duʿāʾ"
        ),

        DuaItem(
            id = "family_6",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Gratitude & Protection",
                hindi = "कृतज्ञता और सुरक्षा",
                urdu = "شکرگزاری اور حفاظت",
                malaysian = "Kesyukuran & Perlindungan"
            ),
            arabic = "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ الَّتِي أَنْعَمْتَ عَلَيَّ وَعَلَىٰ وَالِدَيَّ",
            transliteration = "Rabbi awzi‘nī an ashkura ni‘matakallatī an‘amta ‘alayya wa ‘alā wālidayya",
            translation = LocalizedDuaText(
                english = "My Lord, enable me to be grateful for Your favor upon me and my parents.",
                hindi = "हे मेरे पालनहार, मुझे अपनी और मेरे माता-पिता पर दी हुई नेमतों का शुक्र अदा करने की तौफ़ीक़ दे।",
                urdu = "اے میرے رب، مجھے اپنی اور میرے والدین پر کی ہوئی نعمتوں کا شکر ادا کرنے کی توفیق دے۔",
                malaysian = "Wahai Tuhanku, bantulah aku bersyukur atas nikmat-Mu kepadaku dan ibu bapaku."
            ),
            source = "Qur’an 27:19"
        ),

        DuaItem(
            id = "family_7",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Love & Mercy Between Spouses",
                hindi = "पति-पत्नी के बीच प्रेम",
                urdu = "میاں بیوی کے درمیان محبت",
                malaysian = "Kasih Sayang Antara Pasangan"
            ),
            arabic = "رَبَّنَا أَلِّفْ بَيْنَ قُلُوبِنَا",
            transliteration = "Rabbana allif bayna qulūbinā",
            translation = LocalizedDuaText(
                english = "Our Lord, unite our hearts.",
                hindi = "हे हमारे पालनहार, हमारे दिलों को जोड़ दे।",
                urdu = "اے ہمارے رب، ہمارے دلوں کو جوڑ دے۔",
                malaysian = "Wahai Tuhan kami, satukanlah hati kami."
            ),
            source = "Qur’an 3:103 (meaning-based)"
        ),

        DuaItem(
            id = "family_8",
            categoryId = "family",
            title = LocalizedDuaText(
                english = "Children on Straight Path",
                hindi = "संतानों को सीधा मार्ग",
                urdu = "اولاد کو سیدھا راستہ",
                malaysian = "Anak-anak di Jalan Yang Lurus"
            ),
            arabic = "اللَّهُمَّ اهْدِ أَوْلَادَنَا وَأَصْلِحْهُمْ",
            transliteration = "Allāhumma ihdi awlādanā wa aṣliḥhum",
            translation = LocalizedDuaText(
                english = "O Allah, guide our children and make them righteous.",
                hindi = "ऐ अल्लाह, हमारी संतानों को हिदायत दे और उन्हें नेक बना।",
                urdu = "اے اللہ، ہماری اولاد کو ہدایت دے اور انہیں نیک بنا۔",
                malaysian = "Wahai Allah, berilah petunjuk kepada anak-anak kami dan jadikan mereka soleh."
            ),
            source = "Traditional Duʿāʾ"
        )
    )

    // protectionDuas
    val protectionDuas = listOf(

        DuaItem(
            id = "protect_1",
            categoryId = "protection",
            title = LocalizedDuaText(
                english = "Protection From Harm",
                hindi = "हानि से सुरक्षा",
                urdu = "نقصان سے حفاظت",
                malaysian = "Perlindungan Dari Bahaya"
            ),
            arabic = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ",
            transliteration = "A‘ūdhu bikalimātillāhit-tāmmāt",
            translation = LocalizedDuaText(
                english = "I seek refuge in the perfect words of Allah.",
                hindi = "मैं अल्लाह के पूर्ण शब्दों की पनाह मांगता हूँ।",
                urdu = "میں اللہ کے کامل کلمات کی پناہ مانگتا ہوں۔",
                malaysian = "Aku berlindung dengan kalimah Allah yang sempurna."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "protect_2",
            categoryId = "protection",
            title = LocalizedDuaText(
                english = "Protection From Shayṭān",
                hindi = "शैतान से सुरक्षा",
                urdu = "شیطان سے حفاظت",
                malaysian = "Perlindungan Dari Syaitan"
            ),
            arabic = "اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ",
            transliteration = "Allahummaḥ-faẓnī min bayni yadayya",
            translation = LocalizedDuaText(
                english = "O Allah, protect me from all directions.",
                hindi = "ऐ अल्लाह, मुझे हर दिशा से सुरक्षित रखें।",
                urdu = "اے اللہ، مجھے ہر طرف سے محفوظ رکھ۔",
                malaysian = "Wahai Allah, lindungilah aku dari segala arah."
            ),
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "protect_3",
            categoryId = "protection",
            title = LocalizedDuaText(
                english = "Night Protection",
                hindi = "रात की सुरक्षा",
                urdu = "رات کی حفاظت",
                malaysian = "Perlindungan Malam"
            ),
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ",
            transliteration = "Bismillāhilladhī lā yaḍurru ma‘asmihī shay’",
            translation = LocalizedDuaText(
                english = "In the name of Allah, with whose name nothing can harm.",
                hindi = "अल्लाह के नाम से, जिसके नाम से कोई चीज़ नुकसान नहीं पहुँचा सकती।",
                urdu = "اللہ کے نام سے، جس کے نام کے ساتھ کوئی چیز نقصان نہیں پہنچا سکتی۔",
                malaysian = "Dengan nama Allah yang dengan nama-Nya tiada sesuatu pun dapat memudaratkan."
            ),
            source = "Abu Dawud"
        )
    )

    // ️ Forgiveness Duʿāʾs
    val forgivenessDuas = listOf(

        DuaItem(
            id = "forgive_1",
            categoryId = "forgiveness",
            title = LocalizedDuaText(
                english = "Seek Complete Forgiveness",
                hindi = "पूर्ण क्षमा मांगना",
                urdu = "مکمل معافی مانگنا",
                malaysian = "Memohon Keampunan Penuh"
            ),
            arabic = "رَبِّ اغْفِرْ لِي وَتُبْ عَلَيَّ",
            transliteration = "Rabbi-ghfir lī wa tub ‘alayya",
            translation = LocalizedDuaText(
                english = "My Lord, forgive me and accept my repentance.",
                hindi = "हे प्रभु, मुझे क्षमा करें और मेरा पश्चातव स्वीकारें।",
                urdu = "میرے پروردگار، مجھے بخش دیں اور میری توبہ قبول کریں۔",
                malaysian = "Wahai Tuhanku, ampuni saya dan terima taubat saya."
            ),
            source = "Qur'an 110:3"
        ),

        DuaItem(
            id = "forgive_2",
            categoryId = "forgiveness",
            title = LocalizedDuaText(
                english = "Forgive All Sins",
                hindi = "सभी गुनाहों को क्षमा करें",
                urdu = "تمام گناہوں کو معاف کرو",
                malaysian = "Ampuni Semua Dosa"
            ),
            arabic = "اللَّهُمَّ اغْفِرْ لِي ذَنْبِي كُلَّهُ",
            transliteration = "Allahumma-ghfir lī dhanbī kullah",
            translation = LocalizedDuaText(
                english = "O Allah, forgive all of my sins.",
                hindi = "ऐ अल्लाह, मेरे सभी गुनाहों को क्षमा करें।",
                urdu = "اے اللہ، میرے تمام گناہوں کو معاف فرما۔",
                malaysian = "Wahai Allah, ampuni semua dosa saya."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "forgive_3",
            categoryId = "forgiveness",
            title = LocalizedDuaText(
                english = "Wipe Away Sins",
                hindi = "पापों को मिटाएं",
                urdu = "گناہوں کو مٹا دینا",
                malaysian = "Hilangkan Dosa"
            ),
            arabic = "اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي",
            transliteration = "Allahumma innī ẓalamtu nafsī",
            translation = LocalizedDuaText(
                english = "O Allah, I have wronged myself.",
                hindi = "ऐ अल्लाह, मैंने खुद को गलत किया है।",
                urdu = "اے اللہ، میں نے اپنے آپ کو غلط کیا ہے۔",
                malaysian = "Wahai Allah, aku telah menganiaya diriku sendiri."
            ),
            source = "Bukhari & Muslim"
        ),

        DuaItem(
            id = "forgive_5",
            categoryId = "forgiveness",
            title = LocalizedDuaText(
                english = "Forgiveness Before Death",
                hindi = "मृत्यु से पहले क्षमा",
                urdu = "موت سے پہلے معافیت",
                malaysian = "Kemaafan Sebelum Kematian"
            ),
            arabic = "رَبِّ اغْفِرْ وَارْحَمْ",
            transliteration = "Rabbi-ghfir warḥam",
            translation = LocalizedDuaText(
                english = "My Lord, forgive and have mercy.",
                hindi = "हे प्रभु, क्षमा करें और दया करें।",
                urdu = "میرے پروردگار، معاف فرما اور رحم کرو۔",
                malaysian = "Wahai Tuhanku, ampuni dan kasihanilah."
            ),
            source = "Traditional"
        )
    )

    //  Marriage Duʿāʾs
    val marriageDuas = listOf(

        DuaItem(
            id = "marriage_1",
            categoryId = "marriage",
            title = LocalizedDuaText(
                english = "Righteous Spouse",
                hindi = "धर्मपूर्ण जीवनसाथी",
                urdu = "نیک شوہر",
                malaysian = "Pasangan Yang Soleh"
            ),
            arabic = "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا قُرَّةَ أَعْيُنٍ",
            transliteration = "Rabbana hab lanā min azwājinā qurrata a'yun",
            translation = LocalizedDuaText(
                english = "Our Lord, grant us from our spouses comfort to our eyes.",
                hindi = "हे प्रभु, हमारी पत्नी/पति से हमारी आँखों को आराम दें।",
                urdu = "ہمارے پروردگار سے ہماری آنکھوں سے ہماری آنکھیں کو آرام دیں۔",
                malaysian = "Wahai Tuhan kami, kurniakan kami dari pasangan kami keselesaan mata kami."
            ),
            source = "Qur'an 25:74"
        ),

        DuaItem(
            id = "marriage_2",
            categoryId = "marriage",
            title = LocalizedDuaText(
                english = "Blessed Marriage",
                hindi = "आशीर्वादित विवाह",
                urdu = "برکت شادی",
                malaysian = "Perkahwinan Yang Diberkati"
            ),
            arabic = "بَارَكَ اللَّهُ لَكَ وَبَارَكَ عَلَيْكَ",
            transliteration = "Bārakallāhu laka wa bāraka 'alayk",
            translation = LocalizedDuaText(
                english = "May Allah bless you and send blessings upon you.",
                hindi = "अल्लाह आपको आशीर्वाद दे और आप पर रहमत भेजें।",
                urdu = "اللہ آپ کو برکت دے اور آپ پر رحمت بھیجیے۔",
                malaysian = "Semoga Allah memberkati engkau dan mengurniakan rahmat ke atas engkau."
            ),
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "marriage_3",
            categoryId = "marriage",
            title = LocalizedDuaText(
                english = "Love & Mercy",
                hindi = "प्रेम और दया",
                urdu = "محبت اور رحم",
                malaysian = "Cinta & Kasihan"
            ),
            arabic = "رَبَّنَا أَلِّفْ بَيْنَ قُلُوبِنَا",
            transliteration = "Rabbana allif bayna qulūbinā",
            translation = LocalizedDuaText(
                english = "Our Lord, unite our hearts.",
                hindi = "हे प्रभु, हमारे दिलों को एक करें।",
                urdu = "ہمارے پروردگار، ہمارے دلوں کو ایک کرو۔",
                malaysian = "Wahai Tuhan kami, satukan hati kami."
            ),
            source = "Meaning-based (Qur'an 3:103)"
        )
    )

    // Parents Duʿāʾs
    val parentsDuas = listOf(

        DuaItem(
            id = "parents_1",
            categoryId = "parents",
            title = LocalizedDuaText(
                english = "Forgive My Parents",
                hindi = "मेरे माता-पिता की क्षमा करें",
                urdu = "میرے والدین کو معاف کرو",
                malaysian = "Ampuni Ibu Bapa Saya"
            ),
            arabic = "رَبِّ اغْفِرْ لِي وَلِوَالِدَيَّ",
            transliteration = "Rabbi-ghfir lī wa liwālidayya",
            translation = LocalizedDuaText(
                english = "My Lord, forgive me and my parents.",
                hindi = "हे मेरे पालनहार, मुझे और मेरे माता-पिता को क्षमा करें।",
                urdu = "اے میرے رب، مجھے اور میرے والدین کو معاف فرما۔",
                malaysian = "Wahai Tuhanku, ampuni aku dan ibu bapaku."
            ),
            source = "Qur’an 14:41"
        ),

        DuaItem(
            id = "parents_2",
            categoryId = "parents",
            title = LocalizedDuaText(
                english = "Mercy Upon Parents",
                hindi = "माता-पिता पर दया",
                urdu = "والدین پر رحم",
                malaysian = "Rahmat Kepada Ibu Bapa"
            ),
            arabic = "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيْنِي صَغِيرًا",
            transliteration = "Rabbi irḥamhumā kamā rabbayānī ṣaghīrā",
            translation = LocalizedDuaText(
                english = "My Lord, have mercy upon them as they raised me when I was small.",
                hindi = "हे मेरे पालनहार, उन पर दया कर जैसे उन्होंने मुझे बचपन में पाला।",
                urdu = "اے میرے رب، ان پر رحم فرما جیسے انہوں نے مجھے بچپن میں پالا۔",
                malaysian = "Wahai Tuhanku, kasihanilah mereka sebagaimana mereka membesarkanku ketika kecil."
            ),
            source = "Qur’an 17:24"
        ),

        DuaItem(
            id = "parents_3",
            categoryId = "parents",
            title = LocalizedDuaText(
                english = "Gratitude for Parents",
                hindi = "माता-पिता के लिए आभार",
                urdu = "والدین کے لئے شکر",
                malaysian = "Kesyukuran Kepada Ibu Bapa"
            ),
            arabic = "أَنِ اشْكُرْ لِي وَلِوَالِدَيْكَ",
            transliteration = "Ani-shkur lī wa liwālidayk",
            translation = LocalizedDuaText(
                english = "Be grateful to Me and to your parents.",
                hindi = "मेरे और अपने माता-पिता के प्रति कृतज्ञ रहो।",
                urdu = "میرے اور اپنے والدین کے شکر گزار بنو۔",
                malaysian = "Bersyukurlah kepada-Ku dan kepada ibu bapamu."
            ),
            source = "Qur’an 31:14"
        )
    )

    //  Health & Healing Duʿāʾs
    val healthDuas = listOf(

        DuaItem(
            id = "health_1",
            categoryId = "health",
            title = LocalizedDuaText(
                english = "Healing Duʿāʾ",
                hindi = "शिफ़ा की दुआ",
                urdu = "شفا کی دعا",
                malaysian = "Doa Penyembuhan"
            ),
            arabic = "اللَّهُمَّ اشْفِنِي شِفَاءً لَا يُغَادِرُ سَقَمًا",
            transliteration = "Allahumma-shfinī shifā’an lā yughadiru saqamā",
            translation = LocalizedDuaText(
                english = "O Allah, cure me with a healing that leaves no illness.",
                hindi = "ऐ अल्लाह, मुझे ऐसी शिफ़ा अता कर जो कोई बीमारी न छोड़े।",
                urdu = "اے اللہ، مجھے ایسی شفا عطا فرما جو کوئی بیماری نہ چھوڑے۔",
                malaysian = "Wahai Allah, sembuhkanlah aku dengan kesembuhan yang tidak meninggalkan penyakit."
            ),
            source = "Bukhari"
        ),

        DuaItem(
            id = "health_2",
            categoryId = "health",
            title = LocalizedDuaText(
                english = "Remove Pain",
                hindi = "दर्द दूर करें",
                urdu = "درد دور کریں",
                malaysian = "Hilangkan Kesakitan"
            ),
            arabic = "أَذْهِبِ الْبَاسَ رَبَّ النَّاسِ",
            transliteration = "Adhhib-il-ba’s rabban-nās",
            translation = LocalizedDuaText(
                english = "Remove the harm, O Lord of mankind.",
                hindi = "हे इंसानों के पालनहार, दर्द को दूर कर दें।",
                urdu = "اے انسانوں کے رب، تکلیف کو دور فرما۔",
                malaysian = "Hilangkanlah kesakitan, wahai Tuhan seluruh manusia."
            ),
            source = "Muslim"
        ),

        DuaItem(
            id = "health_3",
            categoryId = "health",
            title = LocalizedDuaText(
                english = "Complete Wellbeing",
                hindi = "पूर्ण स्वास्थ्य",
                urdu = "مکمل صحت",
                malaysian = "Kesihatan Menyeluruh"
            ),
            arabic = "اللَّهُمَّ عَافِنِي فِي بَدَنِي",
            transliteration = "Allahumma ‘āfinī fī badanī",
            translation = LocalizedDuaText(
                english = "O Allah, grant me health in my body.",
                hindi = "ऐ अल्लाह, मेरे शरीर में पूर्ण स्वास्थ्य अता कर।",
                urdu = "اے اللہ، میرے جسم میں مکمل صحت عطا فرما۔",
                malaysian = "Wahai Allah, kurniakanlah kesihatan pada tubuh badanku."
            ),
            source = "Tirmidhi"
        )
    )

    //  Combined list
    val duas =
        ramadanDuas + quranDuas + dailyDuas + quranRabbanaDuas + hisnulMuslimDuas + prophetDuas + morningEveningAdhkar + faithGuidanceDuas + familyDuas + forgivenessDuas + marriageDuas + parentsDuas + protectionDuas + healthDuas

}
