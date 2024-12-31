package com.dobby.backend.infrastructure.database.entity.enum.areaInfo

enum class Area (val region: Region, val displayName: String){
    // 서울
    SEOUL_ALL(Region.SEOUL, "서울 전체"),
    GEUMCHEONGU(Region.SEOUL, "금천구"),
    NOWONGU(Region.SEOUL, "노원구"),
    DOBONGGU(Region.SEOUL, "도봉구"),
    DONGDAEMUNGU(Region.SEOUL, "동대문구"),
    DONGJAKGU(Region.SEOUL, "동작구"),
    MAPOGU(Region.SEOUL, "마포구"),
    SEODAEMUNGU(Region.SEOUL, "서대문구"),
    SEOCHOGU(Region.SEOUL, "서초구"),
    SEONGDONGGU(Region.SEOUL, "성동구"),
    SEONGBUKGU(Region.SEOUL, "성북구"),
    SONGPAGU(Region.SEOUL, "송파구"),
    YANGCHEONGU(Region.SEOUL, "양천구"),
    YEONGDEUNGPOGU(Region.SEOUL, "영등포구"),
    YONGSANGU(Region.SEOUL, "용산구"),
    EUNPYEONGGU(Region.SEOUL, "은평구"),
    JONGNOGU(Region.SEOUL, "종로구"),
    JUNGGU(Region.SEOUL, "중구"),
    JUNGRANGGU(Region.SEOUL, "중랑구"),

    // 경기
    GYEONGGI_ALL(Region.GYEONGGI, "경기 전체"),
    GAPYEONGGUN(Region.GYEONGGI, "가평군"),
    GOYANG_DEOKYANGGU(Region.GYEONGGI, "고양시 덕양구"),
    GOYANG_ILSANDONGGU(Region.GYEONGGI, "고양시 일산동구"),
    GOYANG_ILSANSEOGU(Region.GYEONGGI, "고양시 일산서구"),
    GWACHEONSI(Region.GYEONGGI, "과천시"),
    GWANGMYEONGSI(Region.GYEONGGI, "광명시"),
    GWANGJUSI(Region.GYEONGGI, "광주시"),
    GURISI(Region.GYEONGGI, "구리시"),
    GUNPOSI(Region.GYEONGGI, "군포시"),
    GIMPOSI(Region.GYEONGGI, "김포시"),
    NAMYANGJUSI(Region.GYEONGGI, "남양주시"),
    DONGDUCHEONSI(Region.GYEONGGI, "동두천시"),
    BUCHEONSI(Region.GYEONGGI, "부천시"),
    SEONGNAM_BUNDANGGU(Region.GYEONGGI, "성남시 분당구"),
    SEONGNAM_SUJEONGGU(Region.GYEONGGI, "성남시 수정구"),
    SEONGNAM_JUNGWONGU(Region.GYEONGGI, "성남시 중원구"),
    SUWON_GWONSEONGU(Region.GYEONGGI, "수원시 권선구"),
    SUWON_YEONGTONGGU(Region.GYEONGGI, "수원시 영통구"),
    SUWON_JANGANGU(Region.GYEONGGI, "수원시 장안구"),
    SUWON_PALDALGU(Region.GYEONGGI, "수원시 팔달구"),
    SIHEUNGSI(Region.GYEONGGI, "시흥시"),
    ANSAN_DANWONGU(Region.GYEONGGI, "안산시 단원구"),
    ANSAN_SANGROKGU(Region.GYEONGGI, "안산시 상록구"),
    ANSEONGSI(Region.GYEONGGI, "안성시"),
    ANYANG_DONGANGU(Region.GYEONGGI, "안양시 동안구"),
    ANYANG_MANANGU(Region.GYEONGGI, "안양시 만안구"),
    YANGJUSI(Region.GYEONGGI, "양주시"),
    YANGPYEONGGUN(Region.GYEONGGI, "양평군"),
    YEOJUSI(Region.GYEONGGI, "여주시"),
    YEONCHEONGUN(Region.GYEONGGI, "연천군"),
    OSANSI(Region.GYEONGGI, "오산시"),
    YONGIN_GIHEUNGGU(Region.GYEONGGI, "용인시 기흥구"),
    YONGIN_SUJIGU(Region.GYEONGGI, "용인시 수지구"),
    YONGIN_CHEOINGU(Region.GYEONGGI, "용인시 처인구"),
    UIWANGSI(Region.GYEONGGI, "의왕시"),
    UIJEONGBUSI(Region.GYEONGGI, "의정부시"),
    ICHEONSI(Region.GYEONGGI, "이천시"),
    PAJUSI(Region.GYEONGGI, "파주시"),
    PYEONGTAEKSI(Region.GYEONGGI, "평택시"),
    POCHEONSI(Region.GYEONGGI, "포천시"),
    HANAMSI(Region.GYEONGGI, "하남시"),
    HWASEONGSI(Region.GYEONGGI, "화성시"),

    // 인천광역시
    INCHOEN_ALL(Region.INCHEON, "인천 전체"),
    GANGHWAGUN(Region.INCHEON, "강화군"),
    GYEGU(Region.INCHEON, "계양구"),
    NAMDONGGU(Region.INCHEON, "남동구"),
    DONGGU(Region.INCHEON, "동구"),
    MICHUHOLGU(Region.INCHEON, "미추홀구"),
    BUPYEONGGU(Region.INCHEON, "부평구"),
    SEOGU(Region.INCHEON, "서구"),
    YEONSUGU(Region.INCHEON, "연수구"),
    ONGJINGUN(Region.INCHEON, "옹진군"),
    INCHEON_JUNGGU(Region.INCHEON, "중구"),

    // 강원도
    GANGWON_ALL(Region.SEOUL, "강원 전체"),
    GANGNEUNGSI(Region.GANGWON, "강릉시"),
    GANGWON_GOSEONGGUN(Region.GANGWON, "고성군"),
    DONGHAESI(Region.GANGWON, "동해시"),
    SAMCHEOKSI(Region.GANGWON, "삼척시"),
    SOKCHOSI(Region.GANGWON, "속초시"),
    YANGGUGUN(Region.GANGWON, "양구군"),
    YANGYANGGUN(Region.GANGWON, "양양군"),
    YEONGWOLGUN(Region.GANGWON, "영월군"),
    WONJU(Region.GANGWON, "원주시"),
    INJEGUN(Region.GANGWON, "인제군"),
    JEONGSEONGUN(Region.GANGWON, "정선군"),
    CHEORWONGUN(Region.GANGWON, "철원군"),
    CHUNCHEONSI(Region.GANGWON, "춘천시"),
    TAEBAEKSI(Region.GANGWON, "태백시"),
    PYEONGCHANGGUN(Region.GANGWON, "평창군"),
    HONGCHEONGUN(Region.GANGWON, "홍천군"),
    HWACHEONGUN(Region.GANGWON, "화천군"),
    HOENGSEONGGUN(Region.GANGWON, "횡성군"),

    // 대전광역시
    DAEJEON_ALL(Region.DAEJEON, "대전 전체"),
    DAEJEON_DAEDOKGU(Region.DAEJEON, "대덕구"),
    DAEJEON_DONGGU(Region.DAEJEON, "동구"),
    DAEJEON_SEOGU(Region.DAEJEON, "서구"),
    DAEJEON_YUSEONGGU(Region.DAEJEON, "유성구"),
    DAEJEON_JUNGGU(Region.DAEJEON, "중구"),

    // 세종특별자치시
    SEJONG(Region.SEJONG, "세종시"),

    // 충청남도
    CHUNGNAM_ALL(Region.CHUNGNAM, "충남 전체"),
    GYERYONGSI(Region.CHUNGNAM, "계룡시"),
    GONGJUSI(Region.CHUNGNAM, "공주시"),
    GEUMSANGUN(Region.CHUNGNAM, "금산군"),
    NONSANSI(Region.CHUNGNAM, "논산시"),
    DANGJINSI(Region.CHUNGNAM, "당진시"),
    BORYEONGSI(Region.CHUNGNAM, "보령시"),
    BUYEO(Region.CHUNGNAM, "부여군"),
    SEOSANSI(Region.CHUNGNAM, "서산시"),
    SEOCHEONGUN(Region.CHUNGNAM, "서천군"),
    ASANSI(Region.CHUNGNAM, "아산시"),
    YESANGUN(Region.CHUNGNAM, "예산군"),
    CHEONAN_SEOBUKGU(Region.CHUNGNAM, "천안시 서북구"),
    TAEAN(Region.CHUNGNAM, "태안군"),
    CHEONGYANG(Region.CHUNGNAM, "청양군"),

    // 충청북도
    CHUNGBUK_ALL(Region.CHUNGBUK, "충북 전체"),
    GUAESANGUN(Region.CHUNGBUK, "괴산군"),
    DANYANGGUN(Region.CHUNGBUK, "단양군"),
    BOEUNGUN(Region.CHUNGBUK, "보은군"),
    YEONGDONGGUN(Region.CHUNGBUK, "영동군"),
    OKCHEONGUN(Region.CHUNGBUK, "옥천군"),
    EUMSEONGGUN(Region.CHUNGBUK, "음성군"),
    JECHEONSI(Region.CHUNGBUK, "제천시"),
    JINCHEONGUN(Region.CHUNGBUK, "진천군"),
    CHEONGJU_SANGDANGGU(Region.CHUNGBUK, "청주시 상당구"),
    CHEONGJU_SEOWONGU(Region.CHUNGBUK, "청주시 서원구"),
    CHEONGJU_CHEONGWONGU(Region.CHUNGBUK, "청주시 청원구"),
    CHEONGJU_HEUNGDEOKGU(Region.CHUNGBUK, "청주시 흥덕구"),
    CHUNGJUSI(Region.CHUNGBUK, "충주시"),

    // 경상남도
    GYEONGNAM_ALL(Region.GYEONGNAM, "경남 전체"),
    GEOJESI(Region.GYEONGNAM, "거제시"),
    GEOCHANGGUN(Region.GYEONGNAM, "거창군"),
    GYEONGNAM_GOSEONGGUN(Region.GYEONGNAM, "고성군"),
    GIMHAESI(Region.GYEONGNAM, "김해시"),
    NAMHAEGUN(Region.GYEONGNAM, "남해군"),
    MILYANGSI(Region.GYEONGNAM, "밀양시"),
    SACHEONSI(Region.GYEONGNAM, "사천시"),
    SANCHEONGGUN(Region.GYEONGNAM, "산청군"),
    YANGSAN(Region.GYEONGNAM, "양산시"),
    UIREONGGUN(Region.GYEONGNAM, "의령군"),
    JINJUSI(Region.GYEONGNAM, "진주시"),
    CHANGYEONGGUN(Region.GYEONGNAM, "창녕군"),
    CHANGWON_MASANHAPPOGU(Region.GYEONGNAM, "창원시 마산합포구"),
    CHANGWON_MASANHOEWONGU(Region.GYEONGNAM, "창원시 마산회원구"),
    CHANGWON_SEONGSANGU(Region.GYEONGNAM, "창원시 성산구"),
    CHANGWON_UICHANGGU(Region.GYEONGNAM, "창원시 의창구"),
    CHANGWON_JINHAEGU(Region.GYEONGNAM, "창원시 진해구"),
    TONGYEONGSI(Region.GYEONGNAM, "통영시"),
    HADONGGUN(Region.GYEONGNAM, "하동군"),
    HAMANGUN(Region.GYEONGNAM, "함안군"),
    HAMYANGGUN(Region.GYEONGNAM, "함양군"),
    HAPCHEONGUN(Region.GYEONGNAM, "합천군"),

    // 경상북도
    GYEONGBUK_ALL(Region.GYEONGBUK, "경북 전체"),
    GYEONGSANSI(Region.GYEONGBUK, "경산시"),
    GYEONGJUSI(Region.GYEONGBUK, "경주시"),
    GORYEONGGUN(Region.GYEONGBUK, "고령군"),
    GUMISI(Region.GYEONGBUK, "구미시"),
    GIMCHEONSI(Region.GYEONGBUK, "김천시"),
    MUNGYEONGSI(Region.GYEONGBUK, "문경시"),
    BONGHWAGUN(Region.GYEONGBUK, "봉화군"),
    SANGJUSI(Region.GYEONGBUK, "상주시"),
    SEONGJUGUN(Region.GYEONGBUK, "성주군"),
    ANDONGSI(Region.GYEONGBUK, "안동시"),
    YEONGDEOKGUN(Region.GYEONGBUK, "영덕군"),
    YEONGYANGGUN(Region.GYEONGBUK, "영양군"),
    YEONGJUSI(Region.GYEONGBUK, "영주시"),
    YEONGCHEONSI(Region.GYEONGBUK, "영천시"),
    YECHEONGUN(Region.GYEONGBUK, "예천군"),
    ULLEUNGGUN(Region.GYEONGBUK, "울릉군"),
    ULJINGUN(Region.GYEONGBUK, "울진군"),
    UISEONGGUN(Region.GYEONGBUK, "의성군"),
    CHEONGDOGUN(Region.GYEONGBUK, "청도군"),
    CHEONGSONGGUN(Region.GYEONGBUK, "청송군"),
    CHILGOKGUN(Region.GYEONGBUK, "칠곡군"),
    POHANG_NAMGU(Region.GYEONGBUK, "포항시 남구"),
    POHANG_BUKGU(Region.GYEONGBUK, "포항시 북구"),

    // 전라남도
    JEONNAM_ALL(Region.JEONNAM, "전남 전체"),
    GANGJINGUN(Region.JEONNAM, "강진군"),
    GOHEUNGGUN(Region.JEONNAM, "고흥군"),
    GOKSEONGGUN(Region.JEONNAM, "곡성군"),
    GWANGYANGSI(Region.JEONNAM, "광양시"),
    GURYEOGUN(Region.JEONNAM, "구례군"),
    NAJUSI(Region.JEONNAM, "나주시"),
    DAMYANGGUN(Region.JEONNAM, "담양군"),
    MOKPOSI(Region.JEONNAM, "목포시"),
    MUANGUN(Region.JEONNAM, "무안군"),
    BOSEONGGUN(Region.JEONNAM, "보성군"),
    SUNCHEONSI(Region.JEONNAM, "순천시"),
    SHINANGUN(Region.JEONNAM, "신안군"),
    YEOSUSI(Region.JEONNAM, "여수시"),
    YEONGGWANGGUN(Region.JEONNAM, "영광군"),
    YEONGAMGUN(Region.JEONNAM, "영암군"),
    WANDO(Region.JEONNAM, "완도군"),
    JANGSEONG(Region.JEONNAM, "장성군"),
    JANGHEUNG(Region.JEONNAM, "장흥군"),
    JINDOGUN(Region.JEONNAM, "진도군"),
    HAMPYEONGGUN(Region.JEONNAM, "함평군"),
    HAENAMGUN(Region.JEONNAM, "해남군"),
    HWASUNGUN(Region.JEONNAM, "화순군"),

    // 전라북도
    JEONBUK_ALL(Region.JEONBUK, "전북 전체"),
    GOCHANGGUN(Region.JEONBUK, "고창군"),
    GUNSAN(Region.JEONBUK, "군산시"),
    GIMJESI(Region.JEONBUK, "김제시"),
    NAMWONSI(Region.JEONBUK, "남원시"),
    MUJUGUN(Region.JEONBUK, "무주군"),
    BUAN(Region.JEONBUK, "부안군"),
    SUNCHEON(Region.JEONBUK, "순창군"),
    WANJU(Region.JEONBUK, "완주군"),
    IKSANSI(Region.JEONBUK, "익산시"),
    IMSIL(Region.JEONBUK, "임실군"),
    JANGSU(Region.JEONBUK, "장수군"),
    JEONJU_DEOKJINGU(Region.JEONBUK, "전주시 덕진구"),
    JEONJU_WANSANGU(Region.JEONBUK, "전주시 완산구"),
    JEONGEUP(Region.JEONBUK, "정읍시"),
    JINAN(Region.JEONBUK, "진안군"),

    // 대구광역시
    DAEGU_ALL(Region.DAEGU, "대구 전체"),
    GUNWEEGUN(Region.DAEGU, "군위군"),
    DAEGU_NAMGU(Region.DAEGU, "남구"),
    DAEGU_DALSEOGU(Region.DAEGU, "달서구"),
    DAEGU_DALSEONGGUN(Region.DAEGU, "달성군"),
    DAEGU_DONGGU(Region.DAEGU, "동구"),
    DAEGU_BUKGU(Region.DAEGU, "북구"),
    DAEGU_SEOGU(Region.DAEGU, "서구"),
    DAEGU_SUSEONGGU(Region.DAEGU, "수성구"),
    DAEGU_JUNGGU(Region.DAEGU, "중구"),

    // 광주광역시
    GWANGJU_ALL(Region.GWANGJU, "광주 전체"),
    GWANGJU_GWANGSANGU(Region.GWANGJU, "광산구"),
    GWANGJU_NAMGU(Region.GWANGJU, "남구"),
    GWANGJU_DONGGU(Region.GWANGJU, "동구"),
    GWANGJU_BUKGU(Region.GWANGJU, "북구"),
    GWANGJU_SEOGU(Region.GWANGJU, "서구"),

    // 제주특별자치도
    JEJU_SEOGWIPOSI(Region.JEJU, "서귀포시"),
    JEJU_JEJUSI(Region.JEJU, "제주시");
}
