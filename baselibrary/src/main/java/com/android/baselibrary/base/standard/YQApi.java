package com.android.baselibrary.base.standard;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ClassName: ActivityInject<p>
 * Author: oubowu<p>
 * Fuction: Activity、Fragment初始化的用到的注解<p>
 * CreateDate: 2016/2/15 23:30<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface YQApi {

    /**
     * 顶部局的id
     *
     * @return
     */
    int view() default -1;

    /**
     * 是否支持侧滑关闭
     *
     * @return
     */
    boolean swipeback() default false;


    /**
     * 启动动画
     *
     * 0-3D翻转
     * 1-底部滑入
     * 2-顶部滑入
     * 3-左侧滑入
     * 4-右侧滑入
     * 5-淡入淡出
     * 6-中心缩放
     * 7-上下翻转
     * 8-左上角缩放
     * 9-震动模式
     * 10-左侧中心旋转
     * 11-左上角旋转
     * 12-中心旋转
     * 13-横向展开
     * 14-纵向展开
     */
    int openAnimation() default -1;

    /**
     * 关闭动画
     *
     * @return
     */
    int closAnimatione() default -1;
}
