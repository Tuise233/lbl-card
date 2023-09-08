package cn.tuise233.enums;

public enum MissionQueryType {

    ALL(0),
    ONLY_UNFINISH(1),
    ONLY_FINISHED(2),
    ONLY_FINISHED_DAY(3),
    ONLY_FINISHED_MONTH(4),

    ;

    int code;
    MissionQueryType(int code){
        this.code = code;
    }

    public int getCode(){ return this.code; }

    public static MissionQueryType intToType(Integer number){
        switch(number){
            case 0: return ALL;
            case 1: return ONLY_UNFINISH;
            case 2: return ONLY_FINISHED;
            case 3: return ONLY_FINISHED_DAY;
            case 4: return ONLY_FINISHED_MONTH;
        }
        return ALL;
    }
}
