package dao;

import static constant.Constants.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelDao {

	public Workbook inputExcel(String fileName) throws IOException {
		FileInputStream fi = null;
		Workbook book = null;
		try {
			fi = new FileInputStream(EXCEL_FILE_PATH + fileName);
			book = new HSSFWorkbook(fi);
			fi.close();
		} catch (OfficeXmlFileException e) {
			throw new OfficeXmlFileException(
					"[ERROR] Please make the file \"xls\" format instead of \"xlsx\" format. / ファイルは「xlsx」形式ではなく「xls」形式にしてください。");
		} finally {
			if (fi != null) {
				fi.close();
			}
		}
		return book;
	}

	public void outputExcel(Workbook book, String path, String fileName) throws IOException {
		FileOutputStream fo = null;
		try {
			// ここから出力処理
			fo = new FileOutputStream(path + fileName);
			book.write(fo);
		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		} finally {
			// 最後はちゃんと閉じておきます
			if (fo != null) {
				fo.close();
			}
			if (book != null) {
				book.close();
			}
		}
	}

	public Map<String, Workbook> inputExcelList(String path, String fileName, List<String> vpNames) throws IOException {
		FileInputStream fi = null;
		Workbook book = null;
		Map<String, Workbook> vpBooks = new LinkedHashMap<String, Workbook>();
		try {
			for (String vpName : vpNames) {
				fi = new FileInputStream(path + fileName);
				book = new HSSFWorkbook(fi);
				fi.close();
				vpBooks.put(vpName, book);
			}
		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		} catch (OfficeXmlFileException e) {
			System.out.println(
					"[ERROR] Please make the file \"xls\" format instead of \"xlsx\" format. / ファイルは「xlsx」形式ではなく「xls」形式にしてください。");
			e.printStackTrace();
		} catch (IOException e) {
			//
			e.printStackTrace();
		} finally {
			// 最後はちゃんと閉じておきます
			if (fi != null) {
				fi.close();
			}
		}
		return vpBooks;
	}
}
