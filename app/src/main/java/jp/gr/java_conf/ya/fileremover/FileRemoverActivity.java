package jp.gr.java_conf.ya.fileremover; // Copyright (c) 2012-2016 YA <ya.androidapp@gmail.com> All rights reserved.

import java.io.File;

import jp.gr.java_conf.ya.fileremover.SelectFileDialog.onSelectFileDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class FileRemoverActivity extends Activity implements onSelectFileDialogListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SelectFile();
	}

	protected SelectFileDialog _dlgSelectFile;

	private void SelectFile() {
		_dlgSelectFile = new SelectFileDialog(this);
		_dlgSelectFile.Show(getString(R.string.path));
	}

	public void onFileSelected_by_SelectFileDialog(final File file) {
		if (file != null) {
			final String fileName = file.getName();
			new AlertDialog.Builder(FileRemoverActivity.this)
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						public void onCancel(DialogInterface dialog) {
							finish();
						}
					})
					.setTitle("ファイル操作ダイアログ")
					.setMessage(fileName + "を削除しますか？")
					.setPositiveButton("はい", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							try {
								file.delete();
								Toast.makeText(FileRemoverActivity.this, fileName + "を削除しました", Toast.LENGTH_SHORT)
										.show();
							} catch (Exception e) {
								Toast.makeText(FileRemoverActivity.this, fileName + "を削除できませんでした", Toast.LENGTH_SHORT)
										.show();
							}
							_dlgSelectFile = null;
							finish();
						}
					})
					.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							_dlgSelectFile = null;
							finish();
						}
					})
					.create()
					.show();
		} else {
			_dlgSelectFile = null;
			finish();
		}
	}

	@Override
	public void onPause() {
		if (_dlgSelectFile != null) {
			_dlgSelectFile.onPause();
		}
		super.onPause();
	}
}
