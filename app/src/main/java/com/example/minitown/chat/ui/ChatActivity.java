package com.example.minitown.chat.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.minitown.R;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ChatActivity extends AppCompatActivity {
    private EditText editText;
    private ImageView enterButton;
    private Handler mHandler;
    InetAddress serverAddr;
    Socket socket;
    PrintWriter sendWriter;
    private String ip = "http://54.180.98.98";
    private int port = 8000;

    TextView textView;
    String UserID;
    ImageView imageView;
    TextView chatView;
    EditText message;
    String sendmsg;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            textView = (TextView) findViewById(R.id.txt_sent_message);
            message = (EditText) findViewById(R.id.et_chat);
            Intent intent = getIntent();
            UserID = intent.getStringExtra("username");
            imageView = (ImageView) findViewById(R.id.img_send_chat);



            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendmsg = message.getText().toString();
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sendWriter.println(UserID +">"+ sendmsg);
                                sendWriter.flush();
                                message.setText("");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            });
        }

        class msgUpdate implements Runnable{
            private String msg;
            public msgUpdate(String str) {this.msg=str;}

            @Override
            public void run() {
                chatView.setText(chatView.getText().toString()+msg+"\n");
            }
        }
    }












//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.minitown.R;
//import com.example.minitown.chat.ui.data.ChatAdapter;
//import com.example.minitown.chat.ui.data.ChatModel;
//import com.github.nkzawa.emitter.Emitter;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//
//import io.socket.client.IO;
//import io.socket.client.Socket;
//
//
//public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
//    private static final int MESSAGE_SENDER_ME = 1;
//    private static final int MESSAGE_SENDER_OTHER = 2;
//    private RecyclerView mRecyclerView;
//    private ImageView sendImage;
//    private EditText et_send;
//    private ChatAdapter chatAdapter;
//    private ArrayList<ChatModel> mChatList = new ArrayList<>();
//    private Socket mSocket;
//    private boolean isConnected;
//    private String myUserName = "devmins";
//    {
//        try {
//            mSocket = IO.socket("http://54.180.98.98:8000");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        setContentView(R.layout.activity_chat);
//        super.onCreate(savedInstanceState);
//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_chat);
//        et_send = (EditText)findViewById(R.id.et_chat);
//        sendImage = (ImageView)findViewById(R.id.img_send_chat);
//        sendImage.setOnClickListener(this);
//
//        mSocket.connect();
//        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(layoutManager);
//        ChatAdapter chatAdapter = new ChatAdapter(mChatList);
//        mRecyclerView.setAdapter(chatAdapter);
//    }
//
//    private void onSocketConnect() {
//        mSocket.on(Socket.EVENT_CONNECT, (io.socket.emitter.Emitter.Listener) onConnect);
//        mSocket.on(Socket.EVENT_DISCONNECT, (io.socket.emitter.Emitter.Listener) onDisconnected);
//        mSocket.on(Socket.EVENT_CONNECT_ERROR, (io.socket.emitter.Emitter.Listener) onConnectionError);
//        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, (io.socket.emitter.Emitter.Listener) onConnectionError);
//        mSocket.on("new message", (io.socket.emitter.Emitter.Listener) onNewMessage);
//        mSocket.connect();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mSocket.disconnect();
//        mSocket.off(Socket.EVENT_CONNECT, (io.socket.emitter.Emitter.Listener) onConnect);
//        mSocket.off(Socket.EVENT_DISCONNECT, (io.socket.emitter.Emitter.Listener) onDisconnected);
//        mSocket.off(Socket.EVENT_CONNECT_ERROR, (io.socket.emitter.Emitter.Listener) onConnectionError);
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, (io.socket.emitter.Emitter.Listener) onConnectionError);
//        mSocket.off("new message", (io.socket.emitter.Emitter.Listener) onNewMessage);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.img_send_chat:
//                String message = et_send.getText().toString();
//                if (message!=null && !TextUtils.isEmpty(message)){
//                    mChatList.add(new ChatModel(message, myUserName, MESSAGE_SENDER_ME));
//                    et_send.setText("");
//                    mSocket.emit("new message", message);
//                    if (chatAdapter!=null)
//                        chatAdapter.notifyDataSetChanged();
//                }
//                break;
//        }
//    }
//    private Emitter.Listener onConnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (!isConnected){
//                        mSocket.emit("add user", "myUsername");
//                        Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
//                        isConnected = true;
//                    }
//                }
//            });
//        }
//    };
//
//    private Emitter.Listener onDisconnected = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    isConnected = false;
//                    Toast.makeText(getApplicationContext(), "Disconnected", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    };
//
//    private Emitter.Listener onConnectionError = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getApplicationContext(),"Connection Error", Toast.LENGTH_SHORT);
//                }
//            });
//        }
//    };
//
//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject dataRecieved = (JSONObject) args[0];
//                    String userName, message;
//                    try{
//                        userName = dataRecieved.getString("username");
//                        message = dataRecieved.getString("message");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        return;
//                    }
//
//                    Toast.makeText(getApplicationContext(), userName, Toast.LENGTH_SHORT).show();
//                    mChatList.add(new ChatModel(message, myUserName, MESSAGE_SENDER_OTHER));
//                    if (chatAdapter!=null)
//                        chatAdapter.notifyDataSetChanged();
//                }
//            });
//        }
//    };
//}
//
