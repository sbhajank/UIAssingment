import { Component } from '@angular/core';
import {AppService} from './app.service'

@Component({
  selector: 'login-form',
  providers: [AppService],  
  template: `
    <div class="col-sm-6">
        <h1>Auto Login enable</h1>
    </div>`
})
export class LoginComponent {
    public loginData = {username: "", password: ""};

    constructor(private _service:AppService) {}
 
    login() {
        this._service.obtainAccessToken(this.loginData);
    }
}
