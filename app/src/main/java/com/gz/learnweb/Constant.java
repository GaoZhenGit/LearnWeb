package com.gz.learnweb;

/**
 * 常量储存处
 * Created by host on 2015/8/8.
 */
public interface Constant {
    class ServerIp {
        public static final boolean isWin = false;
        public static final String IP = isWin ? "192.168.1.101" : "139.129.18.117";
    }

    class URL {
        public static final String Register = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=register";
//        public static final String Register = "http://2.learncloud.sinaapp.com/registerCheck.php";
        public static final String Login = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=login";
        //        public static final String Login="http://2.learncloud.sinaapp.com/login.php";
//        public static final String RequestCourse="http://2.learncloud.sinaapp.com/postRequest.php";
//        public static final String RequestCourse="http://1.marketonhand.sinaapp.com/requestTest.php";
        public static final String RequestCourse = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=firstpagecourse";
        public static final String RequestMore = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=requestmore";
        public static final String SearchCourse = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=search";
        public static final String ModifyUser = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=user_modify";
        public static final String AddHistory = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=addHistory";
        public static final String GetHistory = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=getHistory";
        public static final String DeleteHistory = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=deleteHistory";
        public static final String GetCollection = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=getCollection";
        public static final String AddCollection = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=addCollection";
        public static final String DellectCollection = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=deleteCollection";
        public static final String Note = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=note&opration=";
        public static final String Interest = "http://"+ServerIp.IP+"/LearnCloud/index.php?m=Home&c=Index&a=interest&opration=";
    }

    class CODE {
        public static final String KeyValue = "key_value";
        public static final int NameModfiy = 0x578;
        public static final int DetailModify = 0x553;
    }

    class FragmentType {
        public static final String PhoneLogin = "phone_login";
        public static final String EmailLogin = "email_login";
    }

    class DataKey {
        public static final String USER = "user";
        public static final String COURSE = "course";
        public static final String COURSE_LIST_CACHE = "course_list_cache";
        public static final String COURSE_LINE_CACHE = "course_line_cache";
        public static final String FIRSTSTART = "first_start";
        public static final String NOTE = "note";
        public static final String POPULARIZATION = "popularization";
        public static final String HISTORY = "history";
        public static final String NOTIFISTART = "notifi_start";
        public static final String SESS = "sess";
        public static final String MODIFYTYPE = "modify_type";
        public static final String MODIFYRESULT = "modify_result";
    }
}
