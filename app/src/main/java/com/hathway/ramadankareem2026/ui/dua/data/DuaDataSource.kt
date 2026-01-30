package com.hathway.ramadankareem2026.ui.dua.data

import com.hathway.ramadankareem2026.ui.dua.model.DuaItem


object DuaDataSource {

    //  Ramadan
    val ramadanDuas = listOf(

        DuaItem(
            id = "ramadan_moon",
            categoryId = "ramadan",
            title = "Ramadan Moon Sighting",
            arabic = "اللَّهُمَّ أَهْلِلْهُ عَلَيْنَا بِالْيُمْنِ وَالإِيمَانِ وَالسَّلَامَةِ وَالإِسْلَامِ",
            transliteration = "Allahumma ahlilhu ‘alaynā bil-yumni wal-īmān was-salāmati wal-islām",
            translation = "O Allah, bring it upon us with blessing, faith, safety, and Islam.",
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "sehri",
            categoryId = "ramadan",
            title = "Intention for Fasting (Sehri)",
            arabic = "وَبِصَوْمِ غَدٍ نَوَيْتُ مِنْ شَهْرِ رَمَضَانَ",
            transliteration = "Wa bisawmi ghadin nawaitu min shahri Ramaḍān",
            translation = "I intend to keep the fast for tomorrow in the month of Ramadan.",
            source = "Fiqh"
        ),

        DuaItem(
            id = "iftar",
            categoryId = "ramadan",
            title = "Duʿāʾ at Iftar",
            arabic = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ وَثَبَتَ الْأَجْرُ",
            transliteration = "Dhahabaẓ-ẓama’u wabtallatil-‘urūq wa thabatal-ajru",
            translation = "The thirst has gone, the veins are moistened, and the reward is assured.",
            source = "Abu Dawood"
        ),

        DuaItem(
            id = "laylat_qadr",
            categoryId = "ramadan",
            title = "Laylat al-Qadr",
            arabic = "اللَّهُمَّ إِنَّكَ عَفُوٌّ تُحِبُّ الْعَفْوَ فَاعْفُ عَنِّي",
            transliteration = "Allahumma innaka ‘afuwwun tuḥibbul-‘afwa fa‘fu ‘annī",
            translation = "O Allah, You are Most Forgiving, and You love forgiveness, so forgive me.",
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "first_ashra",
            categoryId = "ramadan",
            title = "First Ashra (Mercy)",
            arabic = "رَبِّ اغْفِرْ وَارْحَمْ",
            transliteration = "Rabbighfir warḥam",
            translation = "My Lord, forgive and have mercy.",
            source = "Traditional"
        ),

        DuaItem(
            id = "second_ashra",
            categoryId = "ramadan",
            title = "Second Ashra (Forgiveness)",
            arabic = "أَسْتَغْفِرُ اللّٰهَ رَبِّي مِنْ كُلِّ ذَنْبٍ",
            transliteration = "Astaghfirullāha rabbī min kulli dhamb",
            translation = "I seek forgiveness from Allah for every sin.",
            source = "Traditional"
        ),

        DuaItem(
            id = "third_ashra",
            categoryId = "ramadan",
            title = "Third Ashra (Protection)",
            arabic = "اللَّهُمَّ أَجِرْنِي مِنَ النَّارِ",
            transliteration = "Allahumma ajirnī min an-nār",
            translation = "O Allah, protect me from the Fire.",
            source = "Traditional"
        )
    )

    //  Qur’an – Rabbana Duʿāʾs
    val quranDuas = listOf(

        DuaItem(
            id = "rabbana_1",
            categoryId = "quran",
            title = "Accept From Us",
            arabic = "رَبَّنَا تَقَبَّلْ مِنَّا",
            transliteration = "Rabbana taqabbal minnā",
            translation = "Our Lord, accept from us.",
            source = "Qur’an 2:127"
        ),

        DuaItem(
            id = "rabbana_2",
            categoryId = "quran",
            title = "Guide Our Hearts",
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا",
            transliteration = "Rabbana lā tuzigh qulūbanā",
            translation = "Our Lord, do not let our hearts deviate.",
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "rabbana_3",
            categoryId = "quran",
            title = "Forgive Us",
            arabic = "رَبَّنَا اغْفِرْ لَنَا ذُنُوبَنَا",
            transliteration = "Rabbana-ghfir lanā dhunūbanā",
            translation = "Our Lord, forgive us our sins.",
            source = "Qur’an 3:16"
        )
    )

    //  Daily Duʿāʾs
    val dailyDuas = listOf(

        DuaItem(
            id = "daily_1",
            categoryId = "daily",
            title = "Morning Protection",
            arabic = "اللَّهُمَّ بِكَ أَصْبَحْنَا",
            transliteration = "Allahumma bika aṣbaḥnā",
            translation = "O Allah, by You we enter the morning.",
            source = "Abu Dawood"
        ),

        DuaItem(
            id = "daily_2",
            categoryId = "daily",
            title = "Before Sleep",
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            transliteration = "Bismika Allahumma amūtu wa aḥyā",
            translation = "In Your name, O Allah, I die and I live.",
            source = "Bukhari"
        )
    )

    //  Qur’an – Rabbana Duʿāʾs (30)
    val quranRabbanaDuas = listOf(

        DuaItem(
            id = "rabbana_1",
            categoryId = "quran",
            title = "Accept From Us",
            arabic = "رَبَّنَا تَقَبَّلْ مِنَّا",
            transliteration = "Rabbana taqabbal minnā",
            translation = "Our Lord, accept from us.",
            source = "Qur’an 2:127"
        ),

        DuaItem(
            id = "rabbana_2",
            categoryId = "quran",
            title = "Make Us Muslims",
            arabic = "رَبَّنَا وَاجْعَلْنَا مُسْلِمَيْنِ لَكَ",
            transliteration = "Rabbana waj‘alnā muslimayni laka",
            translation = "Our Lord, make us Muslims in submission to You.",
            source = "Qur’an 2:128"
        ),

        DuaItem(
            id = "rabbana_3",
            categoryId = "quran",
            title = "Forgive Us",
            arabic = "رَبَّنَا اغْفِرْ لَنَا ذُنُوبَنَا",
            transliteration = "Rabbana-ghfir lanā dhunūbanā",
            translation = "Our Lord, forgive us our sins.",
            source = "Qur’an 3:16"
        ),

        DuaItem(
            id = "rabbana_4",
            categoryId = "quran",
            title = "Do Not Let Hearts Deviate",
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا",
            transliteration = "Rabbana lā tuzigh qulūbanā",
            translation = "Our Lord, do not let our hearts deviate after You have guided us.",
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "rabbana_5",
            categoryId = "quran",
            title = "Bestow Mercy",
            arabic = "وَهَبْ لَنَا مِن لَّدُنكَ رَحْمَةً",
            transliteration = "Wahab lanā min ladunka raḥmah",
            translation = "Grant us mercy from Yourself.",
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "rabbana_6",
            categoryId = "quran",
            title = "Remove Our Burdens",
            arabic = "رَبَّنَا وَلَا تَحْمِلْ عَلَيْنَا إِصْرًا",
            transliteration = "Rabbana wa lā taḥmil ‘alaynā iṣran",
            translation = "Our Lord, do not place upon us a burden like those before us.",
            source = "Qur’an 2:286"
        ),

        DuaItem(
            id = "rabbana_7",
            categoryId = "quran",
            title = "Pardon Us",
            arabic = "رَبَّنَا وَلَا تُؤَاخِذْنَا إِن نَّسِينَا أَوْ أَخْطَأْنَا",
            transliteration = "Rabbana lā tu’ākhidhnā in nasīnā aw akhṭa’nā",
            translation = "Our Lord, do not punish us if we forget or make a mistake.",
            source = "Qur’an 2:286"
        ),

        DuaItem(
            id = "rabbana_8",
            categoryId = "quran",
            title = "Grant Victory",
            arabic = "رَبَّنَا افْرِغْ عَلَيْنَا صَبْرًا",
            transliteration = "Rabbana afrigh ‘alaynā ṣabrā",
            translation = "Our Lord, pour upon us patience.",
            source = "Qur’an 2:250"
        ),

        DuaItem(
            id = "rabbana_9",
            categoryId = "quran",
            title = "Do Not Destroy Us",
            arabic = "رَبَّنَا لَا تُهْلِكْنَا",
            transliteration = "Rabbana lā tuhliknā",
            translation = "Our Lord, do not destroy us.",
            source = "Qur’an 7:155"
        ),

        DuaItem(
            id = "rabbana_10",
            categoryId = "quran",
            title = "Forgive Parents",
            arabic = "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ",
            transliteration = "Rabbana-ghfir lī wa liwālidayya",
            translation = "Our Lord, forgive me and my parents.",
            source = "Qur’an 14:41"
        ),

        DuaItem(
            id = "rabbana_11",
            categoryId = "quran",
            title = "Make Us Grateful",
            arabic = "رَبَّنَا أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ",
            transliteration = "Rabbana awzi‘nī an ashkura ni‘mataka",
            translation = "Our Lord, enable me to be grateful for Your favor.",
            source = "Qur’an 27:19"
        ),

        DuaItem(
            id = "rabbana_12",
            categoryId = "quran",
            title = "Good in This World",
            arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً",
            transliteration = "Rabbana ātinā fid-dunyā ḥasanah",
            translation = "Our Lord, grant us good in this world.",
            source = "Qur’an 2:201"
        ),

        DuaItem(
            id = "rabbana_13",
            categoryId = "quran",
            title = "Save From Hellfire",
            arabic = "وَقِنَا عَذَابَ النَّارِ",
            transliteration = "Wa qinā ‘adhāban-nār",
            translation = "And protect us from the punishment of the Fire.",
            source = "Qur’an 2:201"
        ),

        DuaItem(
            id = "rabbana_14",
            categoryId = "quran",
            title = "Light for Us",
            arabic = "رَبَّنَا أَتْمِمْ لَنَا نُورَنَا",
            transliteration = "Rabbana atmim lanā nūranā",
            translation = "Our Lord, perfect for us our light.",
            source = "Qur’an 66:8"
        ),

        DuaItem(
            id = "rabbana_15",
            categoryId = "quran",
            title = "Forgive Believers",
            arabic = "رَبَّنَا اغْفِرْ لَنَا وَلِإِخْوَانِنَا",
            transliteration = "Rabbana-ghfir lanā wa li-ikhwāninā",
            translation = "Our Lord, forgive us and our brothers in faith.",
            source = "Qur’an 59:10"
        )
    )

    //
    val hisnulMuslimDuas = listOf(

        DuaItem(
            id = "hm_1",
            categoryId = "hisnul",
            title = "Duʿāʾ Before Sleeping",
            arabic = "بِاسْمِكَ رَبِّي وَضَعْتُ جَنْبِي",
            transliteration = "Bismika rabbī waḍa‘tu janbī",
            translation = "In Your name my Lord, I lie down.",
            source = "Bukhari, Muslim"
        ),

        DuaItem(
            id = "hm_2",
            categoryId = "hisnul",
            title = "Duʿāʾ After Waking Up",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا",
            transliteration = "Alhamdu lillahil-ladhī aḥyānā",
            translation = "All praise is for Allah who gave us life after death.",
            source = "Bukhari"
        ),

        DuaItem(
            id = "hm_3",
            categoryId = "hisnul",
            title = "Entering the Bathroom",
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْخُبُثِ",
            transliteration = "Allahumma innī a‘ūdhu bika minal-khubuthi",
            translation = "O Allah, I seek refuge in You from evil and evil-doers.",
            source = "Bukhari, Muslim"
        ),

        DuaItem(
            id = "hm_4",
            categoryId = "hisnul",
            title = "Leaving the Bathroom",
            arabic = "غُفْرَانَكَ",
            transliteration = "Ghufrānaka",
            translation = "I ask You for forgiveness.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "hm_5",
            categoryId = "hisnul",
            title = "Before Eating",
            arabic = "بِسْمِ اللَّهِ",
            transliteration = "Bismillāh",
            translation = "In the name of Allah.",
            source = "Muslim"
        ),

        DuaItem(
            id = "hm_6",
            categoryId = "hisnul",
            title = "After Eating",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا",
            transliteration = "Alhamdu lillahil-ladhī aṭ‘amanā",
            translation = "All praise is for Allah who fed us.",
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "hm_7",
            categoryId = "hisnul",
            title = "Entering the Home",
            arabic = "بِسْمِ اللَّهِ وَلَجْنَا",
            transliteration = "Bismillāhi walajnā",
            translation = "In the name of Allah we enter.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "hm_8",
            categoryId = "hisnul",
            title = "Leaving the Home",
            arabic = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ",
            transliteration = "Bismillāhi tawakkaltu ‘alallāh",
            translation = "In the name of Allah, I place my trust in Allah.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "hm_9",
            categoryId = "hisnul",
            title = "Protection From Harm",
            arabic = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ",
            transliteration = "A‘ūdhu bikalimātillāhit-tāmmāt",
            translation = "I seek refuge in the perfect words of Allah.",
            source = "Muslim"
        ),

        DuaItem(
            id = "hm_10",
            categoryId = "hisnul",
            title = "When Anxious or Distressed",
            arabic = "اللَّهُمَّ إِنِّي عَبْدُكَ",
            transliteration = "Allahumma innī ‘abduka",
            translation = "O Allah, I am Your servant.",
            source = "Ahmad"
        )
    )

    //  Duʿāʾs of the Prophets
    val prophetDuas = listOf(

        DuaItem(
            id = "prop_adam",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Adam (AS)",
            arabic = "رَبَّنَا ظَلَمْنَا أَنفُسَنَا",
            transliteration = "Rabbana ẓalamnā anfusanā",
            translation = "Our Lord, we have wronged ourselves.",
            source = "Qur’an 7:23"
        ),

        DuaItem(
            id = "prop_nuh",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Nūḥ (AS)",
            arabic = "رَبِّ إِنِّي مَغْلُوبٌ فَانتَصِرْ",
            transliteration = "Rabbi innī maghlūbun fa-ntaṣir",
            translation = "My Lord, I am overpowered, so help me.",
            source = "Qur’an 54:10"
        ),

        DuaItem(
            id = "prop_ibrahim_1",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Ibrāhīm (AS)",
            arabic = "رَبِّ اجْعَلْنِي مُقِيمَ الصَّلَاةِ",
            transliteration = "Rabbi ij‘alnī muqīmaṣ-ṣalāh",
            translation = "My Lord, make me an establisher of prayer.",
            source = "Qur’an 14:40"
        ),

        DuaItem(
            id = "prop_ibrahim_2",
            categoryId = "prophets",
            title = "Duʿāʾ for a Righteous Offspring",
            arabic = "رَبِّ هَبْ لِي مِنَ الصَّالِحِينَ",
            transliteration = "Rabbi hab lī minaṣ-ṣāliḥīn",
            translation = "My Lord, grant me righteous offspring.",
            source = "Qur’an 37:100"
        ),

        DuaItem(
            id = "prop_musa",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Mūsā (AS)",
            arabic = "رَبِّ اشْرَحْ لِي صَدْرِي",
            transliteration = "Rabbi ishraḥ lī ṣadrī",
            translation = "My Lord, expand my chest.",
            source = "Qur’an 20:25–28"
        ),

        DuaItem(
            id = "prop_ayyub",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Ayyūb (AS)",
            arabic = "أَنِّي مَسَّنِيَ الضُّرُّ وَأَنتَ أَرْحَمُ الرَّاحِمِينَ",
            transliteration = "Annī massaniyaḍ-ḍurru wa anta arḥamur-rāḥimīn",
            translation = "Indeed, adversity has touched me, and You are the Most Merciful.",
            source = "Qur’an 21:83"
        ),

        DuaItem(
            id = "prop_yunus",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Yūnus (AS)",
            arabic = "لَا إِلَٰهَ إِلَّا أَنتَ سُبْحَانَكَ",
            transliteration = "Lā ilāha illā anta subḥānaka",
            translation = "There is no deity except You; exalted are You.",
            source = "Qur’an 21:87"
        ),

        DuaItem(
            id = "prop_zakariya",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Zakariyyā (AS)",
            arabic = "رَبِّ لَا تَذَرْنِي فَرْدًا",
            transliteration = "Rabbi lā tadharnī fardan",
            translation = "My Lord, do not leave me alone.",
            source = "Qur’an 21:89"
        ),

        DuaItem(
            id = "prop_yusuf",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Yūsuf (AS)",
            arabic = "تَوَفَّنِي مُسْلِمًا وَأَلْحِقْنِي بِالصَّالِحِينَ",
            transliteration = "Tawaffanī musliman wa alḥiqnī biṣ-ṣāliḥīn",
            translation = "Cause me to die as a Muslim and join me with the righteous.",
            source = "Qur’an 12:101"
        ),

        DuaItem(
            id = "prop_sulayman",
            categoryId = "prophets",
            title = "Duʿāʾ of Prophet Sulaymān (AS)",
            arabic = "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ",
            transliteration = "Rabbi awzi‘nī an ashkura ni‘mataka",
            translation = "My Lord, enable me to be grateful for Your favor.",
            source = "Qur’an 27:19"
        )
    )

    //  Morning & Evening Adhkār
    val morningEveningAdhkar = listOf(

        DuaItem(
            id = "adhkar_ayah_kursi",
            categoryId = "adhkar",
            title = "Āyat al-Kursī",
            arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ",
            transliteration = "Allāhu lā ilāha illā Huwa al-Ḥayyul-Qayyūm",
            translation = "Allah—there is no deity except Him, the Ever-Living, the Sustainer.",
            source = "Qur’an 2:255"
        ),

        DuaItem(
            id = "adhkar_three_quls",
            categoryId = "adhkar",
            title = "The Three Quls",
            arabic = "قُلْ هُوَ اللَّهُ أَحَدٌ...",
            transliteration = "Qul huwa Allāhu aḥad…",
            translation = "Say: He is Allah, the One…",
            source = "Bukhari & Muslim"
        ),

        DuaItem(
            id = "adhkar_bismillah",
            categoryId = "adhkar",
            title = "Protection from Harm",
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ",
            transliteration = "Bismillāhilladhī lā yaḍurru ma‘asmihī shay’",
            translation = "In the name of Allah, with whose name nothing can harm.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "adhkar_rabb_allah",
            categoryId = "adhkar",
            title = "Contentment with Allah",
            arabic = "رَضِيتُ بِاللَّهِ رَبًّا",
            transliteration = "Raḍītu billāhi rabbā",
            translation = "I am pleased with Allah as my Lord.",
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_hasbi_allah",
            categoryId = "adhkar",
            title = "Allah is Sufficient",
            arabic = "حَسْبِيَ اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ",
            transliteration = "Ḥasbiyallāhu lā ilāha illā Huwa",
            translation = "Allah is sufficient for me; there is no deity except Him.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "adhkar_astaghfirullah",
            categoryId = "adhkar",
            title = "Seeking Forgiveness",
            arabic = "أَسْتَغْفِرُ اللَّهَ وَأَتُوبُ إِلَيْهِ",
            transliteration = "Astaghfirullāha wa atūbu ilayh",
            translation = "I seek forgiveness from Allah and repent to Him.",
            source = "Bukhari"
        ),

        DuaItem(
            id = "adhkar_subhanallah",
            categoryId = "adhkar",
            title = "Tasbīḥ, Taḥmīd, Takbīr",
            arabic = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
            transliteration = "Subḥānallāhi wa biḥamdih",
            translation = "Glory and praise be to Allah.",
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_dua_light",
            categoryId = "adhkar",
            title = "Dua for Light",
            arabic = "اللَّهُمَّ اجْعَلْ فِي قَلْبِي نُورًا",
            transliteration = "Allāhumma ij‘al fī qalbī nūrā",
            translation = "O Allah, place light in my heart.",
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_evening_protection",
            categoryId = "adhkar",
            title = "Evening Protection",
            arabic = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ",
            transliteration = "Amsaynā wa amsal-mulku lillāh",
            translation = "We have entered the evening and dominion belongs to Allah.",
            source = "Muslim"
        ),

        DuaItem(
            id = "adhkar_morning_dua",
            categoryId = "adhkar",
            title = "Morning Remembrance",
            arabic = "أَصْبَحْنَا وَأَصْبَحَ الْمُلْكُ لِلَّهِ",
            transliteration = "Aṣbaḥnā wa aṣbaḥal-mulku lillāh",
            translation = "We have entered the morning and dominion belongs to Allah.",
            source = "Muslim"
        )
    )

    //  Faith & Guidance Duʿāʾs
    val faithGuidanceDuas = listOf(

        DuaItem(
            id = "faith_1",
            categoryId = "faith",
            title = "Guidance After Belief",
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا بَعْدَ إِذْ هَدَيْتَنَا",
            transliteration = "Rabbana lā tuzigh qulūbanā ba‘da idh hadaytanā",
            translation = "Our Lord, do not let our hearts deviate after You have guided us.",
            source = "Qur’an 3:8"
        ),

        DuaItem(
            id = "faith_2",
            categoryId = "faith",
            title = "Increase in Knowledge",
            arabic = "رَبِّ زِدْنِي عِلْمًا",
            transliteration = "Rabbi zidnī ‘ilmā",
            translation = "My Lord, increase me in knowledge.",
            source = "Qur’an 20:114"
        ),

        DuaItem(
            id = "faith_3",
            categoryId = "faith",
            title = "Firmness of Heart",
            arabic = "يَا مُقَلِّبَ الْقُلُوبِ ثَبِّتْ قَلْبِي عَلَى دِينِكَ",
            transliteration = "Yā Muqallibal qulūb, thabbit qalbī ‘alā dīnik",
            translation = "O Turner of hearts, keep my heart firm upon Your religion.",
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "faith_4",
            categoryId = "faith",
            title = "Righteous Guidance",
            arabic = "اللَّهُمَّ اهْدِنِي وَسَدِّدْنِي",
            transliteration = "Allāhumma ihdinī wa saddidnī",
            translation = "O Allah, guide me and keep me firm.",
            source = "Muslim"
        ),

        DuaItem(
            id = "faith_5",
            categoryId = "faith",
            title = "Light in the Heart",
            arabic = "اللَّهُمَّ اجْعَلْ فِي قَلْبِي نُورًا",
            transliteration = "Allāhumma ij‘al fī qalbī nūrā",
            translation = "O Allah, place light in my heart.",
            source = "Muslim"
        ),

        DuaItem(
            id = "faith_6",
            categoryId = "faith",
            title = "Guidance & Piety",
            arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْهُدَى وَالتُّقَى",
            transliteration = "Allāhumma innī as’alukal-hudā wat-tuqā",
            translation = "O Allah, I ask You for guidance and piety.",
            source = "Muslim"
        ),

        DuaItem(
            id = "faith_7",
            categoryId = "faith",
            title = "True Faith",
            arabic = "اللَّهُمَّ حَبِّبْ إِلَيْنَا الْإِيمَانَ",
            transliteration = "Allāhumma ḥabbib ilaynā al-īmān",
            translation = "O Allah, make faith beloved to us.",
            source = "Qur’an 49:7"
        ),

        DuaItem(
            id = "faith_8",
            categoryId = "faith",
            title = "Protection from Misguidance",
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الضَّلَالِ",
            transliteration = "Allāhumma innī a‘ūdhu bika min aḍ-ḍalāl",
            translation = "O Allah, I seek refuge in You from misguidance.",
            source = "Abu Dawud"
        )
    )

    //  Family & Children Duʿāʾs
    val familyDuas = listOf(

        DuaItem(
            id = "family_1",
            categoryId = "family",
            title = "Righteous Spouse & Children",
            arabic = "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا وَذُرِّيَّاتِنَا قُرَّةَ أَعْيُنٍ",
            transliteration = "Rabbana hab lanā min azwājinā wa dhurriyyātinā qurrata a‘yun",
            translation = "Our Lord, grant us from our spouses and offspring comfort to our eyes.",
            source = "Qur’an 25:74"
        ),

        DuaItem(
            id = "family_2",
            categoryId = "family",
            title = "Protection for Children",
            arabic = "أُعِيذُكُمَا بِكَلِمَاتِ اللَّهِ التَّامَّةِ مِنْ كُلِّ شَيْطَانٍ وَهَامَّةٍ",
            transliteration = "U‘īdhukumā bi kalimātillāhit-tāmmati min kulli shayṭānin wa hāmmah",
            translation = "I seek protection for you in the perfect words of Allah from every devil and harmful thing.",
            source = "Bukhari"
        ),

        DuaItem(
            id = "family_3",
            categoryId = "family",
            title = "Righteous Offspring",
            arabic = "رَبِّ هَبْ لِي مِنَ الصَّالِحِينَ",
            transliteration = "Rabbi hab lī minaṣ-ṣāliḥīn",
            translation = "My Lord, grant me righteous offspring.",
            source = "Qur’an 37:100"
        ),

        DuaItem(
            id = "family_4",
            categoryId = "family",
            title = "Guidance for Family",
            arabic = "رَبَّنَا اغْفِرْ لِي وَلِوَالِدَيَّ وَلِلْمُؤْمِنِينَ",
            transliteration = "Rabbana-ghfir lī wa li wālidayya wa lil-mu’minīn",
            translation = "Our Lord, forgive me, my parents, and the believers.",
            source = "Qur’an 14:41"
        ),

        DuaItem(
            id = "family_5",
            categoryId = "family",
            title = "Barakah in Family",
            arabic = "اللَّهُمَّ بَارِكْ لَنَا فِي أَهْلِنَا وَأَوْلَادِنَا",
            transliteration = "Allāhumma bārik lanā fī ahlinā wa awlādina",
            translation = "O Allah, bless our families and our children.",
            source = "Reported Duʿāʾ"
        ),

        DuaItem(
            id = "family_6",
            categoryId = "family",
            title = "Protection from Harm",
            arabic = "رَبِّ أَوْزِعْنِي أَنْ أَشْكُرَ نِعْمَتَكَ الَّتِي أَنْعَمْتَ عَلَيَّ وَعَلَىٰ وَالِدَيَّ",
            transliteration = "Rabbi awzi‘nī an ashkura ni‘matakallatī an‘amta ‘alayya wa ‘alā wālidayya",
            translation = "My Lord, enable me to be grateful for Your favor upon me and my parents.",
            source = "Qur’an 27:19"
        ),

        DuaItem(
            id = "family_7",
            categoryId = "family",
            title = "Love & Mercy Between Spouses",
            arabic = "رَبَّنَا أَلِّفْ بَيْنَ قُلُوبِنَا",
            transliteration = "Rabbana allif bayna qulūbinā",
            translation = "Our Lord, unite our hearts.",
            source = "Qur’an 3:103 (meaning-based)"
        ),

        DuaItem(
            id = "family_8",
            categoryId = "family",
            title = "Children on Straight Path",
            arabic = "اللَّهُمَّ اهْدِ أَوْلَادَنَا وَأَصْلِحْهُمْ",
            transliteration = "Allāhumma ihdi awlādanā wa aṣliḥhum",
            translation = "O Allah, guide our children and make them righteous.",
            source = "Traditional Duʿāʾ"
        )
    )

    // ️ Forgiveness Duʿāʾs
    val forgivenessDuas = listOf(

        DuaItem(
            id = "forgive_1",
            categoryId = "forgiveness",
            title = "Seek Complete Forgiveness",
            arabic = "رَبِّ اغْفِرْ لِي وَتُبْ عَلَيَّ",
            transliteration = "Rabbi-ghfir lī wa tub ‘alayya",
            translation = "My Lord, forgive me and accept my repentance.",
            source = "Qur’an 110:3"
        ),

        DuaItem(
            id = "forgive_2",
            categoryId = "forgiveness",
            title = "Forgive All Sins",
            arabic = "اللَّهُمَّ اغْفِرْ لِي ذَنْبِي كُلَّهُ",
            transliteration = "Allahumma-ghfir lī dhanbī kullah",
            translation = "O Allah, forgive all of my sins.",
            source = "Muslim"
        ),

        DuaItem(
            id = "forgive_3",
            categoryId = "forgiveness",
            title = "Wipe Away Sins",
            arabic = "اللَّهُمَّ إِنِّي ظَلَمْتُ نَفْسِي",
            transliteration = "Allahumma innī ẓalamtu nafsī",
            translation = "O Allah, I have wronged myself.",
            source = "Bukhari & Muslim"
        ),

        DuaItem(
            id = "forgive_4",
            categoryId = "forgiveness",
            title = "Forgiveness Before Death",
            arabic = "رَبِّ اغْفِرْ وَارْحَمْ",
            transliteration = "Rabbi-ghfir warḥam",
            translation = "My Lord, forgive and have mercy.",
            source = "Traditional"
        )
    )

    //  Marriage Duʿāʾs
    val marriageDuas = listOf(

        DuaItem(
            id = "marriage_1",
            categoryId = "marriage",
            title = "Righteous Spouse",
            arabic = "رَبَّنَا هَبْ لَنَا مِنْ أَزْوَاجِنَا قُرَّةَ أَعْيُنٍ",
            transliteration = "Rabbana hab lanā min azwājinā qurrata a‘yun",
            translation = "Our Lord, grant us from our spouses comfort to our eyes.",
            source = "Qur’an 25:74"
        ),

        DuaItem(
            id = "marriage_2",
            categoryId = "marriage",
            title = "Blessed Marriage",
            arabic = "بَارَكَ اللَّهُ لَكَ وَبَارَكَ عَلَيْكَ",
            transliteration = "Bārakallāhu laka wa bāraka ‘alayk",
            translation = "May Allah bless you and send blessings upon you.",
            source = "Tirmidhi"
        ),

        DuaItem(
            id = "marriage_3",
            categoryId = "marriage",
            title = "Love & Mercy",
            arabic = "رَبَّنَا أَلِّفْ بَيْنَ قُلُوبِنَا",
            transliteration = "Rabbana allif bayna qulūbinā",
            translation = "Our Lord, unite our hearts.",
            source = "Meaning-based (Qur’an 3:103)"
        )
    )

    //  Parents Duʿāʾs
    val parentsDuas = listOf(

        DuaItem(
            id = "parents_1",
            categoryId = "parents",
            title = "Forgive My Parents",
            arabic = "رَبِّ اغْفِرْ لِي وَلِوَالِدَيَّ",
            transliteration = "Rabbi-ghfir lī wa liwālidayya",
            translation = "My Lord, forgive me and my parents.",
            source = "Qur’an 14:41"
        ),

        DuaItem(
            id = "parents_2",
            categoryId = "parents",
            title = "Mercy Upon Parents",
            arabic = "رَبِّ ارْحَمْهُمَا كَمَا رَبَّيَانِي صَغِيرًا",
            transliteration = "Rabbi-rḥamhumā kamā rabbayānī ṣaghīrā",
            translation = "My Lord, have mercy upon them as they raised me when I was small.",
            source = "Qur’an 17:24"
        ),

        DuaItem(
            id = "parents_3",
            categoryId = "parents",
            title = "Gratitude for Parents",
            arabic = "أَنِ اشْكُرْ لِي وَلِوَالِدَيْكَ",
            transliteration = "Ani-shkur lī wa liwālidayk",
            translation = "Be grateful to Me and to your parents.",
            source = "Qur’an 31:14"
        )
    )

    // ️ Protection Duʿāʾs
    val protectionDuas = listOf(

        DuaItem(
            id = "protect_1",
            categoryId = "protection",
            title = "Protection From Harm",
            arabic = "أَعُوذُ بِكَلِمَاتِ اللَّهِ التَّامَّاتِ",
            transliteration = "A‘ūdhu bikalimātillāhit-tāmmāt",
            translation = "I seek refuge in the perfect words of Allah.",
            source = "Muslim"
        ),

        DuaItem(
            id = "protect_2",
            categoryId = "protection",
            title = "Protection From Shayṭān",
            arabic = "اللَّهُمَّ احْفَظْنِي مِنْ بَيْنِ يَدَيَّ",
            transliteration = "Allahummaḥ-faẓnī min bayni yadayya",
            translation = "O Allah, protect me from all directions.",
            source = "Abu Dawud"
        ),

        DuaItem(
            id = "protect_3",
            categoryId = "protection",
            title = "Night Protection",
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ",
            transliteration = "Bismillāhilladhī lā yaḍurru ma‘asmihī shay’",
            translation = "In the name of Allah, with whose name nothing can harm.",
            source = "Abu Dawud"
        )
    )

    //  Health & Healing Duʿāʾs
    val healthDuas = listOf(

        DuaItem(
            id = "health_1",
            categoryId = "health",
            title = "Healing Duʿāʾ",
            arabic = "اللَّهُمَّ اشْفِنِي شِفَاءً لَا يُغَادِرُ سَقَمًا",
            transliteration = "Allahumma-shfinī shifā’an lā yughadiru saqamā",
            translation = "O Allah, cure me with a healing that leaves no illness.",
            source = "Bukhari"
        ),

        DuaItem(
            id = "health_2",
            categoryId = "health",
            title = "Remove Pain",
            arabic = "أَذْهِبِ الْبَاسَ رَبَّ النَّاسِ",
            transliteration = "Adhhib-il-ba’s rabban-nās",
            translation = "Remove the harm, O Lord of mankind.",
            source = "Muslim"
        ),

        DuaItem(
            id = "health_3",
            categoryId = "health",
            title = "Complete Wellbeing",
            arabic = "اللَّهُمَّ عَافِنِي فِي بَدَنِي",
            transliteration = "Allahumma ‘āfinī fī badanī",
            translation = "O Allah, grant me health in my body.",
            source = "Tirmidhi"
        )
    )

    //  Combined list
    val duas =
        ramadanDuas + quranDuas + dailyDuas + quranRabbanaDuas + hisnulMuslimDuas + prophetDuas + morningEveningAdhkar + faithGuidanceDuas + familyDuas + forgivenessDuas + marriageDuas + parentsDuas + protectionDuas + healthDuas

}
