package Server;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youngkyoung.conndb.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import UrlService.ServerUrl;

/**
 * Created by YoungKyoung on 2015-12-30.
 */
public class ConnectServer
{
    private String sendData1 = "";

    private static String serverDatas = "";
    private Activity Act;
    private ParsingRequest parsingRequest;
    private static TextView requestText;

    private ProgressDialog mDlg;

    public void initial(String sendData, Activity activity)
    {

        this.sendData1 = sendData;
        this.Act = activity;

        this.parsingRequest = new ParsingRequest();
        parsingRequest.execute(ServerUrl.serverurl);
    }

    class ParsingRequest extends AsyncTask<String, String, String>
    {

        @Override
        //background 작업 시전에  ui작업을 진행한다.
        protected void onPreExecute()
        {
            mDlg = new ProgressDialog(Act);
            mDlg.setProgressStyle(ProgressDialog.THEME_HOLO_LIGHT);
            mDlg.setMessage("로딩중입니다.....");
            mDlg.show();
            super.onPreExecute();
        }

        @Override
        //background 작업 진행
        protected String doInBackground(String... urlData) {

            try {
                //전송모드 기본적인 방식
                URL url = new URL(ServerUrl.serverurl); //url 설정
                HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //접속
                conn.setDefaultUseCaches(false);
                conn.setDoInput(true); //서버에서 읽기 모드 지정
                conn.setDoOutput(true); //서버애서 쓰기 모드 지정
                conn.setRequestMethod("POST"); //전송방식
                Log.e("http정의", "성공");
                conn.setRequestProperty("content-type",
                        "application/x-www-form-urlencoded");

                //서버로 값보내기
                StringBuffer dataBoxs = new StringBuffer();
                dataBoxs.append("A").append("=").append(sendData1);
                PrintWriter sendWorker = new PrintWriter(
                        new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
                sendWorker.write(dataBoxs.toString());
                sendWorker.flush();

                BufferedReader getWorker = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuilder serverDataBox = new StringBuilder();

                while (true) {
                    String data = getWorker.readLine();
                    if (data == null) {
                        break;
                    }
                    serverDataBox.append(data);
                }
                getWorker.close();
                serverDatas = serverDataBox.toString();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                // Toast.makeText(Act, "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                return "error";
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Toast.makeText(Act, "아이디와 비밀번호를 확인하세요.", Toast.LENGTH_SHORT).show();
                return "error";
                //e.printStackTrace();
                //Log.i("error2", e.toString());
            }


            return serverDatas;
        }

        @Override
        //background 작업이 끝난 후 ui작업
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(result.equals("error")){
                mDlg.dismiss();
                Toast.makeText(Act, "connect error", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(Act, "등록완료", Toast.LENGTH_SHORT).show();
                mDlg.dismiss();
                MainActivity.editxt01.setText("");
               // MainActivity.txt01.setText(result);
            }



        }

    }

}
