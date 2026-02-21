import { Injectable,inject } from '@angular/core';
import { Client,IMessage } from '@stomp/stompjs';
import  SockJS from 'sockjs-client';
@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  private client!:Client
  private connected = false;

   constructor() { }

  connect(callback:(msg:any)=>void){
    if(this.connected){
      return;
    }
    this.client=new Client({
      webSocketFactory:()=>new SockJS('http://localhost:8081/ws'),
      reconnectDelay:5000,
    });

    this.client.onConnect=() =>{
      this.connected = true;
      console.log("connected to ws")
      let user=JSON.parse(localStorage.getItem('user') as any)
      this.client.subscribe(`/topic/alert.${user.id}`,(message:IMessage)=>{
        callback(JSON.parse(message.body));
      });
    };
    this.client.activate();
  }


  isConnected(): boolean {
  return this.connected;
}

  /*
    sendMessage(message:any){
      this.client.publish({
       destination:'/app/send',
       body:JSON.stringify(message)
      });
    }*/

    disconnect(){
      if(this.client && this.client.active){
        this.client.deactivate();
        console.log('WebSocket disconnected')
      }
    }


}
