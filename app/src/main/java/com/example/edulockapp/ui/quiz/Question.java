package com.example.edulockapp.ui.quiz;


public class Question {
    public String questions[] = {
            "Berapa rakaat shalat Shubuh?",
            "2 pangkat 3 adalah?",
            "Presiden Pertama Indonesia adalah",
            "Indonesia masuk kedalam benua?",
            "Apa hukum Puasa Ramadhan, bagi muslim?",
            "Bentuk ban sepeda adalah?",
            "Bahasa inggris kucing adalah?",
            "Mata uang negara Indonesia adalah",
            "Bahasa inggris buah pisang adalah",
            "Lambang negara Indonesia adalah",
            "Warna merah dicampur dengan warna kuning, akan menghasilkan warna…"
    };

    public String choices[][] = {
            {"2 Rakaat", "3 Rakaat", "4 Rakaat", "Tidak ada batasan"},
            {"16", "4", "2", "8"},
            {"Jokowi", "Sukarno", "BJ.Habibi", "Gusdur"},
            {"Eropa", "Australia", "Amerika", "Asia"},
            {"Wajib", "Sunnah", "Mubah", "Makruh"},
            {"Segitiga", "Persegi", "Oval", "Lingkaran"},
            {"Cat", "dog", "Bird", "Ant"},
            {"Yen", "Dollar", "Rupiah", "Peso"},
            {"Grape", "Banana", "Watermelon", "Pineapple"},
            {"Elang", "Macan", "Harimau", "Garuda"},
            {"Merah Muda", "Oranye", "Hijau", "Jingga"},
    };

    public String correctAnswer[] = {
            "2 Rakaat",
            "8",
            "Sukarno",
            "Asia",
            "Wajib",
            "Lingkaran",
            "Cat",
            "Rupiah",
            "Banana",
            "Garuda",
            "Oranye"
    };

    public String getQuestion(int a){
        String question = questions[a];
        return question;
    }

    public String getchoice1(int a){
        String choice = choices[a][0];
        return choice;
    }

    public String getchoice2(int a){
        String choice = choices[a][1];
        return choice;
    }

    public String getchoice3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getchoice4(int a){
        String choice = choices[a][3];
        return choice;
    }


    public String getCorrectAnswer(int a){
        String answer = correctAnswer[a];
        return answer;
    }
}
