package com.example.hpdemo;

public class Constants {
    public static String APPENNAME;

    public static int AppID = 0;

    public static final String CHECKSOFTVERSION = "GetCheckAppVersion";

    public static int COLUMN_BASE = 0;

    public static String DEFAULTMAPURL;

    public static String HTTP_INTERFACE_PORT;

    public static String NAMESPACE;

    public static String NOTIFCATION_ID = "notifcation_id";

    public static String SOFT_UPDATE_ENDPOINT;

    public static String defalutServerIp;

    public static String defalutServerPort;

    static  {
        APPENNAME = "hp_lyt";
        AppID = 4;
        NAMESPACE = "http://tempuri.org/";
        SOFT_UPDATE_ENDPOINT = "http://www.helpsoft.com.cn:8001/WebService.asmx";
        DEFAULTMAPURL = "http://60.173.167.105:6080/arcgis/rest/services/las/M/MapServer";
        HTTP_INTERFACE_PORT = "14491";
        defalutServerIp = "60.173.167.105";
        defalutServerPort = "8066";
    }

    public static class COLLECT_TYPE {
        public static final int FIRECOLLECT;

        public static final int ROAD_COLLECT;

        public static final int SMALLCOLLECT;

        static  {
            int i = Constants.COLUMN_BASE;
            Constants.COLUMN_BASE = i + 1;
            ROAD_COLLECT = i;
            i = Constants.COLUMN_BASE;
            Constants.COLUMN_BASE = i + 1;
            SMALLCOLLECT = i;
            i = Constants.COLUMN_BASE;
            Constants.COLUMN_BASE = i + 1;
            FIRECOLLECT = i;
        }
    }

    public static class IP_CONFIG {
        public static final String API_IP = "60.173.167.105";

        public static final String API_PORT = "9099";

        public static final String IP = "60.173.167.105";

        public static final String PORT = "8066";

        public static final String defalutMapDownloadAddress = "http://60.173.167.105:6181/api/MapList";
    }

    public static class NOTIFACTION_NAME {
        public static final String CARMER_ALARM = "����������";

        private static final String CONFIRM_ALARM = "��������";

        public static final String ENV_CONTROL_ALARM = "������������";

        public static final String FIRE_ALARM = "��������";

        public static final String GEOGRAPHY = "��������";

        public static final String HOTSPOTALARM = "��������";

        public static final String MANUAL_ALARAM = "��������";

        public static final String SOS_ALARAM = "SOS����";

        public static final String SOS_SAVING = "��������";

        public static final String STATION_ALARM = "��������";

        private static final String STOP_ALARM = "��������";

        private static final String SUSPECT_ALARM = "��������";

        public static final String WEATHER_ALARM = "��������";
    }

    public static class NOTIFACTION_TYPE {
        public static int Bayonet_LOCATION;

        public static int CAMERA_ALARM_NOTIFCATION;

        public static int ENV_CONTROL_ALARAM_NOTIFCATION;

        public static int FIRE_ALARAM_NOTIFCATION;

        public static int GEOGRAPHYPOSITIONING;

        public static String GPS_UPLOAD_CHANGED;

        public static int HOT_POINT_NOTIFCATION;

        public static int MANUAL_ALARAM_NOTIFCATION;

        public static int PATROLFOUND_LOCATION;

        public static int RESOURCE_LOCATION;

        public static int SITE_LOCATION;

        public static int SOS_NOTIFCATION;

        public static int SOS_SAVED_NOTIFY;

        public static int STATION_ALARM_NOTIFCATION = 1;

        public static int WEATHER_NOTIFCATION;

        static  {
            SITE_LOCATION = 17;
            Bayonet_LOCATION = 18;
            GEOGRAPHYPOSITIONING = 19;
            SOS_NOTIFCATION = 20;
            SOS_SAVED_NOTIFY = 21;
            PATROLFOUND_LOCATION = 22;
            RESOURCE_LOCATION = 23;
            CAMERA_ALARM_NOTIFCATION = 2;
            MANUAL_ALARAM_NOTIFCATION = 3;
            FIRE_ALARAM_NOTIFCATION = 4;
            WEATHER_NOTIFCATION = 5;
            HOT_POINT_NOTIFCATION = 6;
            ENV_CONTROL_ALARAM_NOTIFCATION = 7;
            GPS_UPLOAD_CHANGED = "gps_upload_changed";
        }
    }
}
