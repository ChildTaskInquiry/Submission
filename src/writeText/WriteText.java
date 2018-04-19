package writeText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

public class WriteText {

	public void writeText(String path, String fileName, String text, String writeCode) {
		try (PrintWriter pw = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(new File(path + fileName)), writeCode)))) {
			text = text.replaceAll("\r", "");
			pw.print(text);

			pw.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "保存ファイルが使用中です。閉じてからもう一度実行してください", "ファイルが使用中です",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

	}

}
