
package io.renren.common.utils;


/**
 * 常量
 *
 * @author Mark sunlightcs@gmail.com
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    /**
     * 学生选报导师，最多同事选报的个数
     */
    public static final int DECLARE_MAX = 2;

	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 系统角色
     */
    public enum SysRoleIds{

        /**
         * 导师
         */
        DAOSHI(1),
        /**
         * 面试教师
         */
        MIANTEA(2),
        /**
         * 机试教师
         */
        JITEA(3),

        /**
         * 卓越生
         */
        ZHUOYUE(4),
        /**
         * 选拔生
         */
        XUANBA(5);



        private long value;

        SysRoleIds(long value){
            this.value = value;
        }

        public long getValue(){return value;}


    }

}
