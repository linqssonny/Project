package com.sonny.project.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sonny.project.R;
import com.library.base.BaseActivity;
import com.library.base.db.SQLiteHelper;
import com.sonnyjack.utils.toast.ToastUtils;

/**
 * Created by admin on 2016/11/8.
 */

public class DBActivity extends BaseActivity {

    private EditText mEtValue;

    @Override
    public int getContentViewId() {
        return R.layout.activity_db;
    }

    @Override
    public void initUI() {
        mEtValue = (EditText) findViewById(R.id.et_db_value);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_db_save:
                //保存
                save();
                break;
            case R.id.btn_db_get:
                //显示
                get();
                break;
        }
    }

    private void get() {
        Cursor cursor = SQLiteHelper.getInstances().query(DBTable.TABLE_NAME, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            ToastUtils.showShortMsg(this, "查询失败");
            return;
        }
        int nameIndex = cursor.getColumnIndex(DBTable.NAME);
        String value = cursor.getString(nameIndex);
        mEtValue.setText(value + SystemClock.elapsedRealtime());
    }

    private void save() {
        String value = mEtValue.getText().toString().trim();
        if (TextUtils.isEmpty(value)) {
            return;
        }
        SQLiteHelper.getInstances().delete(DBTable.TABLE_NAME, null, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBTable.NAME, value);
        SQLiteDatabase sqLiteDatabase = SQLiteHelper.getInstances().getSQLiteDatabase();
        sqLiteDatabase.beginTransaction();
        long rowID = sqLiteDatabase.insert(DBTable.TABLE_NAME, DBTable.ID, contentValues);
        if (rowID == -1) {
            sqLiteDatabase.endTransaction();
            ToastUtils.showShortMsg(this, "保存失败");
            return;
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        ToastUtils.showShortMsg(this, "保存成功");
    }
}
