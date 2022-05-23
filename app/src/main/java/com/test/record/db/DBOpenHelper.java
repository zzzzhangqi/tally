package com.test.record.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.test.record.R;

import java.util.ArrayList;

/**
 * @author 1575825411
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "tally.db", null, 1);
    }

    //    创建数据库的方法，只有项目第一次运行时，会被调用
    private SQLiteDatabase db;

    @Override
    public void onCreate(SQLiteDatabase db) {
//        创建表示类型的表
        String sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)";
        db.execSQL(sql);
        insertType(db);
        //创建记账表
        sql = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,beizhu varchar(80),money float," +
                "time varchar(60),year integer,month integer,day integer,kind integer)";
        db.execSQL(sql);

        //登录
        db.execSQL("create table user(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "password TEXT)");
    }

    private void insertType(SQLiteDatabase db) {
//      向typetb表当中插入元素
        String sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)";
        db.execSQL(sql, new Object[]{"其他", R.mipmap.ic_qita, R.mipmap.ic_qita_fs, 0});
        db.execSQL(sql, new Object[]{"餐饮", R.mipmap.ic_canyin, R.mipmap.ic_canyin_fs, 0});
        db.execSQL(sql, new Object[]{"交通", R.mipmap.ic_jiaotong, R.mipmap.ic_jiaotong_fs, 0});
        db.execSQL(sql, new Object[]{"购物", R.mipmap.ic_gouwu, R.mipmap.ic_gouwu_fs, 0});
        db.execSQL(sql, new Object[]{"服饰", R.mipmap.ic_fushi, R.mipmap.ic_fushi_fs, 0});
        db.execSQL(sql, new Object[]{"日用品", R.mipmap.ic_riyongpin, R.mipmap.ic_riyongpin_fs, 0});
        db.execSQL(sql, new Object[]{"娱乐", R.mipmap.ic_yule, R.mipmap.ic_yule_fs, 0});
        db.execSQL(sql, new Object[]{"零食", R.mipmap.ic_lingshi, R.mipmap.ic_lingshi_fs, 0});
        db.execSQL(sql, new Object[]{"烟酒茶", R.mipmap.ic_yanjiu, R.mipmap.ic_yanjiu_fs, 0});
        db.execSQL(sql, new Object[]{"学习", R.mipmap.ic_xuexi, R.mipmap.ic_xuexi_fs, 0});
        db.execSQL(sql, new Object[]{"医疗", R.mipmap.ic_yiliao, R.mipmap.ic_yiliao_fs, 0});
        db.execSQL(sql, new Object[]{"住宅", R.mipmap.ic_zhufang, R.mipmap.ic_zhufang_fs, 0});
        db.execSQL(sql, new Object[]{"水电煤", R.mipmap.ic_shuidianfei, R.mipmap.ic_shuidianfei_fs, 0});
        db.execSQL(sql, new Object[]{"通讯", R.mipmap.ic_tongxun, R.mipmap.ic_tongxun_fs, 0});
        db.execSQL(sql, new Object[]{"人情往来", R.mipmap.ic_renqingwanglai, R.mipmap.ic_renqingwanglai_fs, 0});

        db.execSQL(sql, new Object[]{"其他", R.mipmap.in_qt, R.mipmap.in_qt_fs, 1});
        db.execSQL(sql, new Object[]{"薪资", R.mipmap.in_xinzi, R.mipmap.in_xinzi_fs, 1});
        db.execSQL(sql, new Object[]{"奖金", R.mipmap.in_jiangjin, R.mipmap.in_jiangjin_fs, 1});
        db.execSQL(sql, new Object[]{"借入", R.mipmap.in_jieru, R.mipmap.in_jieru_fs, 1});
        db.execSQL(sql, new Object[]{"收债", R.mipmap.in_shouzhai, R.mipmap.in_shouzhai_fs, 1});
        db.execSQL(sql, new Object[]{"利息收入", R.mipmap.in_lixifuji, R.mipmap.in_lixifuji_fs, 1});
        db.execSQL(sql, new Object[]{"投资回报", R.mipmap.in_touzi, R.mipmap.in_touzi_fs, 1});
        db.execSQL(sql, new Object[]{"二手交易", R.mipmap.in_ershoushebei, R.mipmap.in_ershoushebei_fs, 1});
        db.execSQL(sql, new Object[]{"意外所得", R.mipmap.in_yiwai, R.mipmap.in_yiwai_fs, 1});
    }

    // 数据库版本在更新时发生改变，会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void add(String name,String password){
        db.execSQL("INSERT INTO user (name,password) VALUES(?,?)",new Object[]{name,password});
    }
    public void delete(String name,String password){
        db.execSQL("DELETE FROM user WHERE name = AND password ="+name+password);
    }
    public void updata(String password){
        db.execSQL("UPDATE user SET password = ?",new Object[]{password});
    }

    /**
     * 前三个没啥说的，都是一套的看懂一个其他的都能懂了
     * 下面重点说一下查询表user全部内容的方法
     * 我们查询出来的内容，需要有个容器存放，以供使用，
     * 所以定义了一个ArrayList类的list
     * 有了容器，接下来就该从表中查询数据了，
     * 这里使用游标Cursor，这就是数据库的功底了，
     * 在Android中我就不细说了，因为我数据库功底也不是很厚，
     * 但我知道，如果需要用Cursor的话，第一个参数："表名"，中间5个：null，
     *                                                     最后是查询出来内容的排序方式："name DESC"
     * 游标定义好了，接下来写一个while循环，让游标从表头游到表尾
     * 在游的过程中把游出来的数据存放到list容器中
     * @return
     */
    public ArrayList<User> getAllData(){

        ArrayList<User> list = new ArrayList<User>();
        Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            list.add(new User(name,password));
        }
        return list;
    }

}
