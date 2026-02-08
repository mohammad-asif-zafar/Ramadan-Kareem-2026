package com.hathway.ramadankareem2026.ui.tips.data.source

import com.hathway.ramadankareem2026.ui.tips.data.model.Tip
import com.hathway.ramadankareem2026.ui.tips.data.model.TipCategory
import com.hathway.ramadankareem2026.ui.tips.data.model.LocalizedText
import java.util.Calendar

object TipsDataSource {

    private val dailyRamadanTips = listOf(
        Tip(
            id = 1, title = LocalizedText(
                english = "Delay Suhoor and Hasten Iftar",
                hindi = "सहरी देर करें और इफ्तार जल्दी करें",
                urdu = "سحری کو دیر کرنا اور افطار جلدی کرنا",
                malaysian = "Tangguh Sahur dan Segerakan Berbuka"
            ), content = LocalizedText(
                english = "The Prophet (ﷺ) said: 'The people will not cease to be upon good as long as they hasten in breaking the fast.' (Bukhari, Muslim)",
                hindi = "पैग़ंबर (ﷺ) ने फ़रमाया: 'लोग उस समय तक अच्छाई पर बने रहेंगे जब तक वे रोज़ा जल्दी तोड़ते रहेंगे।' (बुख़ारी, मुस्लिम)",
                urdu = "نبی (ﷺ) نے فرمایا: 'لوگ اس وقت تک اچھائی پر قائم رہیں گے جب تک وہ روزہ جلدی توڑیں گے۔' (بخاری، مسلم)",
                malaysian = "Rasulullah (ﷺ) bersabda: 'Umat tidak akan berhenti dari kebaikan selama mereka menyegerakan berbuka puasa.' (Bukhari, Muslim)"
            ), category = TipCategory.DAILY_RAMADAN, isDaily = true
        ), Tip(
            id = 2, title = LocalizedText(
                english = "Make Intention (Niyyah) Before Fasting",
                hindi = "रोज़े से पहले नियत (निय्याह) करें",
                urdu = "روزے سے پہلے نیت کریں",
                malaysian = "Buat Niat (Niyyah) Sebelum Puasa"
            ), content = LocalizedText(
                english = "Renew your intention each night: 'I intend to fast tomorrow for the sake of Allah.' A sincere intention transforms your hunger into worship.",
                hindi = "हर रात अपनी नियत नवीनीकृत करें: 'मैं अल्लाह के लिए कल रोज़ा रखने का इरादा रखता हूँ।' एक ईमानदार नियत आपकी भूख को इबादत में बदल देती है।",
                urdu = "ہر رات اپنی نیت تازہ کریں: 'میں اللہ کے لیے کل روزہ رکھنے کا ارادہ رکھتا ہوں۔' ایک صدیقانہ نیت آپ کی بھوک کو عبادت میں تبدیل کر دیتی ہے۔",
                malaysian = "Baharakan niat setiap malam: 'Saya berniat puasa esok kerana Allah.' Niat yang ikhlas mengubah lapar menjadi ibadah."
            ), category = TipCategory.DAILY_RAMADAN, isDaily = true
        ), Tip(
            id = 3, title = LocalizedText(
                english = "Control Anger While Fasting",
                hindi = "रोज़े में गुस्से पर काबू करें",
                urdu = "روزے میں غصے پر قابو پائیں",
                malaysian = "Kawal Marah Semasa Berpuasa"
            ), content = LocalizedText(
                english = "Fasting is not just from food and drink, but also from evil speech and actions. When angered, say: 'I am fasting.'",
                hindi = "रोज़ा सिर्फ खाने-पीने से नहीं, बल्कि बुरी बातों और कर्मों से भी है। जब गुस्सा आए, तो कहें: 'मैं रोज़ा रख रहा हूँ।'",
                urdu = "روزہ صرف کھانے پینے سے نہیں، بلکہ بری گفتار اور اعمال سے بھی ہے۔ جب غصہ آئے تو کہیں: 'میں روزہ رکھ رہا ہوں۔'",
                malaysian = "Puasa bukan sah dari makanan dan minuman, tetapi juga dari percakapan dan perbuatan jahat. Bila marah, katakan: 'Saya sedang berpuasa.'"
            ), category = TipCategory.DAILY_RAMADAN, isDaily = true
        ), Tip(
            id = 4, title = LocalizedText(
                english = "Increase Dhikr Between Prayers",
                hindi = "नमाज़ों के बीच ज़िक्र बढ़ाएं",
                urdu = "نمازوں کے درمیان ذکر بڑھائیں",
                malaysian = "Tingkatkan zikir antara waktu solat"
            ), content = LocalizedText(
                english = "Use the breaks between prayers for remembrance of Allah. Subhan'Allah, Alhamdulillah, Allahu Akbar - these simple words carry immense weight.",
                hindi = "नमाज़ों के बीच के अंतराल का इस्तेमाल अल्लाह की याद के लिए करें। सुब्हान'अल्लाह, अल्हम्दुलिल्लाह, अल्लाहु अकबर - ये सरल शब्द बहुत भारी हैं।",
                urdu = "نمازوں کے درمیان کے وقفے کا استعمال اللہ کی یاد کے لیے کریں۔ سبحان اللہ، الحمد للہ، اللہ اکبر - یہ سادہ الفاظ بہت وزنی ہیں۔",
                malaysian = "Gunakan waktu antara solat untuk mengingati Allah. Subhanallah, Alhamdulillah, Allahu Akbar — lafaz yang ringkas tetapi sangat besar ganjarannya."
            ), category = TipCategory.DAILY_RAMADAN, isDaily = true
        )

    )

    private val fastingTips = listOf(

        Tip(
            id = 101, title = LocalizedText(
                english = "Does Brushing Teeth Break Fast?",
                hindi = "क्या दांत साफ करने से रोज़ा टूटता है?",
                urdu = "کیا دانت صاف کرنے سے روزہ ٹوٹتا ہے؟",
                malaysian = "Adakah Memberus Gigi Membatalkan Puasa?"
            ), content = LocalizedText(
                english = "Using miswak or brushing teeth without swallowing does not break your fast. Be careful not to swallow any water or toothpaste.",
                hindi = "मिस्वाक का उपयोग या बिना निगले दांत साफ करने से आपका रोज़ा नहीं टूटता। पानी या टूथपेस्ट निगलने से बचें।",
                urdu = "مسواک کا استعمال یا بغیر نگلے دانت صاف کرنے سے آپ کا روزہ نہیں ٹوٹتا۔ پانی یا ٹوتھ پیسٹ نگلنے سے بچیں۔",
                malaysian = "Menggunakan miswak atau berus gigi tanpa menelan tidak membatalkan puasa. Berhati-hatilah agar tidak menelan air atau ubat gigi."
            ), category = TipCategory.FASTING
        ),

        Tip(
            id = 102, title = LocalizedText(
                english = "Is Injection Allowed During Fast?",
                hindi = "क्या रोज़े में इंजेक्शन लगवाना मुमकिन है?",
                urdu = "کیا روزے میں انجکشن لگانا جائز ہے؟",
                malaysian = "Adakah Suntikan Dibenarkan Ketika Berpuasa?"
            ), content = LocalizedText(
                english = "Nutritional injections break the fast, but medical injections for treatment (like vaccines or insulin) do not break the fast according to most scholars.",
                hindi = "पोषण इंजेक्शन रोज़ा तोड़ते हैं, लेकिन इलाज के लिए मेडिकल इंजेक्शन (जैसे वैक्सीन या इंसुलिन) अधिकांश विद्वानों के अनुसार रोज़ा नहीं तोड़ते।",
                urdu = "غذائی انجکشن روزہ توڑتے ہیں، لیکن علاج کے لیے طبی انجکشن (جیسے ویکسین یا انسولین) اکثر علماء کے مطابق روزہ نہیں توڑتے۔",
                malaysian = "Suntikan berkhasiat membatalkan puasa, tetapi suntikan perubatan untuk rawatan seperti vaksin atau insulin tidak membatalkan puasa menurut kebanyakan ulama."
            ), category = TipCategory.FASTING
        ),

        Tip(
            id = 103, title = LocalizedText(
                english = "Common Mistakes While Fasting",
                hindi = "रोज़े में होने वाली आम गलतियाँ",
                urdu = "روزے میں ہونے والی عام غلطیاں",
                malaysian = "Kesalahan Biasa Ketika Berpuasa"
            ), content = LocalizedText(
                english = "Avoid: excessive eating at iftar, missing suhoor, wasting Ramadan time, anger and arguments, forgetting evening prayers.",
                hindi = "बचें: इफ्तार में अधिक खाना, सहरी छूटना, रमज़ान का समय बर्बाद करना, गुस्सा और झगड़े, शाम की नमाज़ें भूल जाना।",
                urdu = "سے بچیں: افطار میں زیادہ کھانا، سحری چھوٹنا، رمضان کا وقت ضائع کرنا، غصہ اور جھگڑے، شام کی نمازیں بھول جانا۔",
                malaysian = "Elakkan makan berlebihan ketika berbuka, meninggalkan sahur, mensia-siakan waktu Ramadan, marah dan bertengkar, serta melalaikan solat."
            ), category = TipCategory.FASTING
        ),

        Tip(
            id = 104, title = LocalizedText(
                english = "Medical Allowances",
                hindi = "चिकित्सा छूट",
                urdu = "طبی رعایت",
                malaysian = "Kelonggaran Perubatan"
            ), content = LocalizedText(
                english = "Travelers, menstruating women, the ill, and elderly with health issues are exempt from fasting. Make up missed days when able.",
                hindi = "यात्री, मासिक धर्म में महिलाएं, बीमार, और स्वास्थ्य समस्याओं वाले बुजुर्ग रोज़े से छूट हैं। सक्षम होने पर छूटे हुए दिन पूरे करें।",
                urdu = "مسافر، حیض میں خواتین، مریض، اور صحت کے مسائل سے متاثرہ بزرگ روزے سے مستثنیٰ ہیں۔ جب طاقت ہو تو چھوٹے ہوئے دن پورے کریں۔",
                malaysian = "Musafir, wanita yang sedang haid, orang sakit, dan warga emas yang mempunyai masalah kesihatan diberi kelonggaran daripada berpuasa. Gantilah puasa yang ditinggalkan apabila mampu."
            ), category = TipCategory.FASTING
        ),

        Tip(
            id = 105, title = LocalizedText(
                english = "Sunnah of Fasting",
                hindi = "रोज़े की सुन्नत",
                urdu = "روزے کی سنت",
                malaysian = "Sunnah Dalam Berpuasa"
            ), content = LocalizedText(
                english = "Eat suhoor, break fast with dates, make dua at iftar, pray Taraweeh, increase Quran recitation, give charity, and maintain ties with family.",
                hindi = "सहरी खाएं, खजूर से रोज़ा तोड़ें, इफ्तार पर दुआ करें, तरावीह पढ़ें, कुरान पढ़ना बढ़ाएं, दान करें, और परिवार के साथ रिश्ते बनाए रखें।",
                urdu = "سحری کھائیں، کھجور سے افطار کریں، افطار پر دعا کریں، تراویح پڑھیں، قرآن پڑھنے بڑھائیں، صدقہ دیں، اور خاندان کے ساتھ تعلقات قائم رکھیں۔",
                malaysian = "Makan sahur, berbuka dengan kurma, berdoa ketika berbuka, menunaikan solat tarawih, memperbanyakkan bacaan Al-Quran, bersedekah, dan menjaga hubungan kekeluargaan."
            ), category = TipCategory.FASTING
        )
    )

    private val prayerTips = listOf(

        Tip(
            id = 201, title = LocalizedText(
                english = "How to Pray with Khushuʿ",
                hindi = "खुशू के साथ कैसे नमाज़ पढ़ें",
                urdu = "خشو کے ساتھ کیسے نماز پڑھیں؟",
                malaysian = "Cara Solat Dengan Khusyuk"
            ), content = LocalizedText(
                english = "Focus on the meaning of recitations, understand you're speaking to Allah, pray as if you see Him, and remember death and the grave.",
                hindi = "किरातों के अर्थ पर ध्यान दें, समझें कि आप अल्लाह से बात कर रहे हैं, ऐसे प्रार्थना करें जैसे आप उन्हें देख रहे हैं, और मृत्यु और कब्र को याद रखें।",
                urdu = "قرأت کے معانی پر توجہ دیں، سمجھیں کہ آپ اللہ سے بات کر رہے ہیں، ایسی دعا کریں جیسے آپ اسے دیکھ رہے ہیں، اور موت اور قبر کو یاد رکھیں۔",
                malaysian = "Fokus kepada makna bacaan dalam solat, fahami bahawa anda sedang berbicara dengan Allah, solatlah seolah-olah anda melihat-Nya, serta ingat akan kematian dan alam kubur."
            ), category = TipCategory.PRAYER
        ),

        Tip(
            id = 202, title = LocalizedText(
                english = "Best Duʿās After Salah",
                hindi = "नमाज़ के बाद सर्वोत्तम दुआएँ",
                urdu = "نماز کے بعد بہترین دعائیں",
                malaysian = "Doa Terbaik Selepas Solat"
            ), content = LocalizedText(
                english = "Astaghfirullah (3x), Subhan'Allah (33x), Alhamdulillah (33x), Allahu Akbar (33x), then recite Ayat al-Kursi and make personal du'a.",
                hindi = "अस्तग़फ़िरुल्लाह (3x), सुब्हान'अल्लाह (33x), अल्हम्दुलिल्लाह (33x), अल्लाहु अकबर (33x), फिर आयतुल कुर्सी पढ़ें और व्यक्तिगत दुआ करें।",
                urdu = "استغفراللہ (3x)، سبحان اللہ (33x)، الحمد للہ (33x)، اللہ اکبر (33x)، پھر آیت الکرسی پڑھیں اور ذاتی دعا کریں۔",
                malaysian = "Bacalah Astaghfirullah (3 kali), Subhanallah (33 kali), Alhamdulillah (33 kali), Allahu Akbar (33 kali), kemudian baca Ayat Kursi dan berdoalah dengan doa peribadi."
            ), category = TipCategory.PRAYER
        ),

        Tip(
            id = 203, title = LocalizedText(
                english = "Mistakes in Prayer to Avoid",
                hindi = "नमाज़ में बचने वाली गलतियाँ",
                urdu = "نماز میں سے بچنے والی غلطیاں",
                malaysian = "Kesalahan Dalam Solat Yang Perlu Dielakkan"
            ), content = LocalizedText(
                english = "Rushing through prayers, not focusing, incorrect wudu, missing obligatory parts, praying in forbidden times, and forgetting proper attire.",
                hindi = "नमाज़ में जल्दी करना, ध्यान न देना, गलत वुजू, फ़र्ज़ हिस्से छूटना, निषिद्ध समयों में नमाज़ पढ़ना, और उचित पोशाक भूल जाना।",
                urdu = "نماز میں جلدی کرنا، توجہ نہ دینا، غلط وضو، فرض حصے چھوٹنا، منعوقہ وقتوں میں نماز پڑھنا، اور مناسب لباس بھول جانا۔",
                malaysian = "Solat dengan tergesa-gesa, tidak khusyuk, wuduk yang tidak sempurna, meninggalkan rukun wajib, solat pada waktu yang dilarang, serta mengabaikan pakaian yang menutup aurat."
            ), category = TipCategory.PRAYER
        )
    )

    private val duaDhikrTips = listOf(

        Tip(
            id = 301, title = LocalizedText(
                english = "Best Times to Make Duʿā",
                hindi = "दुआ करने का सर्वोत्तम समय",
                urdu = "دعا کرنے کے بہترین اوقات",
                malaysian = "Waktu Terbaik Untuk Berdoa"
            ), content = LocalizedText(
                english = "During sujood, between adhan and iqamah, on Fridays, while fasting, during rain, in the last third of night, and when traveling.",
                hindi = "सजदे में, अज़ान और इक़ामत के बीच, शुक्रवार को, रोज़े में, बारिश में, रात के आखिरी तिहाई में, और यात्रा के दौरान।",
                urdu = "سجدے میں، اذان اور اقامہ کے درمیان، جمعرات کو، روزے میں، بارش میں، رات کا آخری تہائیں، اور سفر کے دوران۔",
                malaysian = "Ketika sujud, antara azan dan iqamah, pada hari Jumaat, ketika berpuasa, semasa hujan, pada sepertiga malam terakhir, dan ketika dalam perjalanan."
            ), category = TipCategory.DUA_DHIKR
        ),

        Tip(
            id = 302, title = LocalizedText(
                english = "Powerful Dhikr After Fajr",
                hindi = "फज्र के बाद शक्तिशाली ज़िक्र",
                urdu = "فجر کے بعد طاقتور ذکر",
                malaysian = "Zikir Pagi Yang Sangat Berkesan Selepas Subuh"
            ), content = LocalizedText(
                english = "Morning adhkar protect you all day: Ayat al-Kursi, last 3 verses of Al-Baqarah, and 'La ilaha illallah wahdahu la sharika lah.'",
                hindi = "सुबह का ज़िक्र आपको पूरे दिन सुरक्षित रखता है: आयतुल कुर्सी, अल-बक़राह की आखिरी 3 आयतें, और 'ला इलाहा इल्लल्लाह वहदाहू ला शरीका लह।'",
                urdu = "صبح کا ذکر آپ کو پورے دن محفوظ رکھتا ہے: آیت الکرسی، البقرہ کے آخری 3 آیات، اور 'لا الہ الا اللہ وحدہ لا شریک لہ۔'",
                malaysian = "Zikir pagi melindungi anda sepanjang hari: Ayat Kursi, tiga ayat terakhir Surah Al-Baqarah, dan bacaan “La ilaha illallah wahdahu la sharika lah”."
            ), category = TipCategory.DUA_DHIKR
        ),

        Tip(
            id = 303, title = LocalizedText(
                english = "Duʿā Between Adhan and Iqamah",
                hindi = "अज़ान और इक़ामत के बीच दुआ",
                urdu = "دعا اذان اور اقامہ کے درمیان",
                malaysian = "Doa Antara Azan dan Iqamah"
            ), content = LocalizedText(
                english = "This is a time of answered prayers. The du'a made between the call to prayer and the iqamah is never rejected.",
                hindi = "यह क़बूल होने वाली दुआओं का समय है। अज़ान और इक़ामत के बीच की दुआ कभी अस्वीकार नहीं की जाती।",
                urdu = "یہ قبول ہونے والی دعاؤں کا وقت ہے۔ اذان اور اقامہ کے درمیان کی دوا کبھی مسترد نہیں کی جاتی۔",
                malaysian = "Ini adalah waktu doa dimakbulkan. Doa yang dipanjatkan antara azan dan iqamah tidak akan ditolak."
            ), category = TipCategory.DUA_DHIKR
        )
    )

    private val lifestyleTips = listOf(

        Tip(
            id = 401, title = LocalizedText(
                english = "Sleep Schedule During Ramadan",
                hindi = "रमज़ान में नींद का कार्यक्रम",
                urdu = "رمضان میں سونے کا شیڈول",
                malaysian = "Jadual Tidur Sepanjang Ramadan"
            ), content = LocalizedText(
                english = "Sleep after Isha, wake for Tahajjud and suhoor, nap briefly if needed. Quality night sleep beats long daytime naps.",
                hindi = "इशा के बाद सोएं, तहज्जुद और सहरी के लिए जागें, ज़रूरत हो तो थोड़ी देर झपकी लें। गुणवत्तापूर्ण रात की नींद लंबे दिन की झपकी से बेहतर है।",
                urdu = "عشاء کے بعد سویں، تہجد اور سحری کے لیے جاگیں، ضرورت ہو تو چھوٹی سی جھپکی لیں۔ معیاری رات کی نیند لمبی دن کی جھپکی سے بہتر ہے۔",
                malaysian = "Tidur selepas Isyak, bangun untuk Tahajjud dan sahur, dan ambil tidur sekejap jika perlu. Tidur malam yang berkualiti lebih baik daripada tidur siang yang terlalu lama."
            ), category = TipCategory.LIFESTYLE
        ),

        Tip(
            id = 402, title = LocalizedText(
                english = "Healthy Iftar Tips",
                hindi = "स्वस्थ इफ्तार टिप्स",
                urdu = "صحت مند افطار کے ٹپس",
                malaysian = "Tips Iftar Yang Sihat"
            ), content = LocalizedText(
                english = "Start with dates and water, then light soup. Wait 10 minutes before main meal. Avoid heavy fried foods and overeating.",
                hindi = "खजूर और पानी से शुरू करें, फिर हल्का सूप लें। मुख्य भोजन से पहले 10 मिनट इंतज़ार करें। भारी तली हुई खाना और अधिक खाने से बचें।",
                urdu = "کھجور اور پانی سے شروع کریں، پھر ہلکا سوپ لیں۔ اہم کھانے سے پہلے 10 منٹ انتظار کریں۔ بھاری تلے ہوئے کھانے اور زیادہ کھانے سے بچیں۔",
                malaysian = "Mulakan dengan kurma dan air, kemudian sup ringan. Tunggu 10 minit sebelum hidangan utama. Elakkan makanan bergoreng berat dan makan berlebihan."
            ), category = TipCategory.LIFESTYLE
        ),

        Tip(
            id = 403, title = LocalizedText(
                english = "Managing Work While Fasting",
                hindi = "रोज़े में काम का प्रबंधन",
                urdu = "روزے کے دوران کام کا انتظام",
                malaysian = "Mengurus Kerja Ketika Berpuasa"
            ), content = LocalizedText(
                english = "Schedule important tasks for morning hours, avoid meetings late afternoon, communicate your fasting schedule to colleagues.",
                hindi = "महत्वपूर्ण कार्यों को सुबह के घंटों के लिए निर्धारित करें, दोपहर बाद की बैठकों से बचें, अपने रोज़े के कार्यक्रम को सहयोगियों को बताएं।",
                urdu = "اہم کاموں کو صبح کے گھنٹوں کے لیے شیڈول کریں، دوپہر کے بعد میٹنگز سے بچیں، اپنے روزے کے شیڈول کو ساتھیوں سے بتائیں۔",
                malaysian = "Jadualkan tugas penting pada waktu pagi, elakkan mesyuarat lewat petang, dan maklumkan jadual puasa anda kepada rakan sekerja."
            ), category = TipCategory.LIFESTYLE
        ),

        Tip(
            id = 404, title = LocalizedText(
                english = "Avoiding Burnout in Last 10 Nights",
                hindi = "आखिरी 10 रातों में थकावट से बचना",
                urdu = "آخری 10 راتیں میں تھکن سے بچنا",
                malaysian = "Mengelakkan Keletihan Pada 10 Malam Terakhir"
            ), content = LocalizedText(
                english = "Pace yourself, prioritize worship over quantity, maintain energy with proper nutrition, and remember quality over quantity in deeds.",
                hindi = "अपनी गति बनाए रखें, मात्रा पर इबादत को प्राथमिकता दें, उचित पोषण से ऊर्जा बनाए रखें, और अमलों में गुणवत्ता को मात्रा पर प्राथमिकता दें।",
                urdu = "اپنی رفتار برقرار رکھیں، مقدار پر عبادت کو ترجیح دیں، مناسب غذائیت سے توانائی برقرار رکھیں، اور اعمال میں معیار کو مقدار پر ترجیح دیں۔",
                malaysian = "Kawal tenaga diri, utamakan kualiti ibadah berbanding kuantiti, kekalkan tenaga dengan pemakanan yang betul, dan ingat bahawa kualiti amalan lebih penting daripada jumlahnya."
            ), category = TipCategory.LIFESTYLE
        )
    )

    private val hadithAyahTips = listOf(

        Tip(
            id = 501, title = LocalizedText(
                english = "Hadith of the Day",
                hindi = "दिन की हदीस",
                urdu = "دن کی حدیث",
                malaysian = "Hadis Hari Ini"
            ), content = LocalizedText(
                english = "'Whoever fasts Ramadan out of faith and seeking reward, his previous sins will be forgiven.' (Bukhari, Muslim)",
                hindi = "'जो कोई ईमान और पुरस्कार की तलाश में रमज़ान का रोज़ा रखता है, उसके पिछले गुनाह माफ़ कर दिए जाते हैं।' (बुख़ारी, मुस्लिम)",
                urdu = "'جو کوئی ایمان اور جزا کی تلاش میں رمضان کا روزہ رکھتا ہے، اس کے گزشتہ گناہ معاف کر دیے جاتے ہیں۔' (بخاری، مسلم)",
                malaysian = "“Sesiapa yang berpuasa di bulan Ramadan dengan penuh iman dan mengharapkan ganjaran, maka dosa-dosanya yang lalu akan diampunkan.” (Bukhari, Muslim)"
            ), category = TipCategory.HADITH_AYAH
        ),

        Tip(
            id = 502, title = LocalizedText(
                english = "Verse of the Day",
                hindi = "दिन की आयत",
                urdu = "دن کی آیت",
                malaysian = "Ayat Hari Ini"
            ), content = LocalizedText(
                english = "'O you who believe, fasting has been prescribed for you as it was prescribed for those before you, that you may become righteous.' (Quran 2:183)",
                hindi = "'ऐ ईमानदारों, तुम पर रोज़े का हुक्म उसी तरह है जैसा तुमसे पहले लोगों पर हुआ था, ताकि तुम परहेज़गार बन सको।' (क़ुरान 2:183)",
                urdu = "'اے لوگو جو ایمان لائے، تم پر روزوں کا حکم ویسا ہی ہے جیسا تم سے پہلے لوگوں پر تھا، تاکہ تم تقویٰ پاؤ۔' (قرآن ۲:۱۸۳)",
                malaysian = "“Wahai orang-orang yang beriman, diwajibkan ke atas kamu berpuasa sebagaimana diwajibkan ke atas orang-orang sebelum kamu, agar kamu bertakwa.” (Al-Quran 2:183)"
            ), category = TipCategory.HADITH_AYAH
        )
    )

    private val goodDeedsTips = listOf(

        Tip(
            id = 601, title = LocalizedText(
                english = "Daily Good Deeds Checklist",
                hindi = "दैनिक अच्छे कामों की चेकलिस्ट",
                urdu = "روزانہ اچھے اعمال کی چیک لسٹ",
                malaysian = "Senarai Amalan Baik Harian"
            ), content = LocalizedText(
                english = "✔ Smile at Muslims\n✔ Give charity (even a smile)\n✔ Forgive someone\n✔ Call family members\n✔ Share beneficial knowledge\n✔ Make dua for others",
                hindi = "✔ मुसलमानों पर मुस्कुराएं\n✔ दान करें (यहाँ तक कि मुस्कान भी)\n✔ किसी को माफ़ करें\n✔ परिवार के सदस्यों को कॉल करें\n✔ लाभदायक ज्ञान साझा करें\n✔ दूसरों के लिए दुआ करें",
                urdu = "✔ مسلمانوں پر مسکرائیں\n✔ صدقہ دیں (حتی مسکرائی بھی)\n✔ کسی کو معاف کریں\n✔ خاندان کے ارکان کو کال کریں\n✔ مفید علم شیئر کریں\n✔ دوسروں کے لیے دعا کریں",
                malaysian = "✔ Senyum kepada sesama Muslim\n✔ Bersedekah (walaupun hanya dengan senyuman)\n✔ Memaafkan seseorang\n✔ Hubungi ahli keluarga\n✔ Berkongsi ilmu yang bermanfaat\n✔ Mendoakan kebaikan untuk orang lain"
            ), category = TipCategory.GOOD_DEEDS
        )
    )


    fun getAllTips(): List<Tip> {
        return dailyRamadanTips + fastingTips + prayerTips + duaDhikrTips + lifestyleTips + hadithAyahTips + goodDeedsTips
    }

    fun getDailyTip(): Tip {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val tipIndex = dayOfYear % dailyRamadanTips.size
        return dailyRamadanTips[tipIndex]
    }

    fun getHadithOrAyahOfTheDay(): Tip {
        val calendar = Calendar.getInstance()
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val tipIndex = dayOfYear % hadithAyahTips.size
        return hadithAyahTips[tipIndex]
    }
}
