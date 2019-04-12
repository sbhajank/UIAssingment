import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { Cookie } from 'ng2-cookies';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

export class Foo {
  constructor(
    public amount: number,
    public currency: string,
    public destination: string,
    public origin: string,
    public originLocation:any,
    public destinationLocation:any
  ) { }
}
export class Location {
  constructor(
    public code: string,
    public name: string,
    public description: string,
    public coordinates: any,
    public parent:Location,
    public children:Location[]
  ){}
}
export class ApiInfo {
  constructor(
    public path:string,
    public count200:number,
    public count500:number,
    public count400:number,
    ){}
}
export class ApiStatusSummery {
  constructor(
    public path:string,
    public method:string,
    public status:number,
    public count:number,
  ){}
}
@Injectable()
export class AppService {
  constructor(
    private _router: Router, private _http: HttpClient){}

  obtainAccessToken(loginData){
    let params = new URLSearchParams();
    // params.append('username',loginData.username);
    // params.append('password',loginData.password);
    params.append('grant_type','client_credentials');
    // params.append('client_id','travel-api-client');

    const httpOptions = {
      body:params,
      withCredentials: true,
      headers : new HttpHeaders({
        'Content-Type':  'application/x-www-form-urlencoded; charset=utf-8',
        'Authorization': 'Basic dHJhdmVsLWFwaS1jbGllbnQ6cHN3'
      })

    };
  // ,
  //   'Access-Control-Allow-Methods':'GET, POST, DELETE, PUT'
    console.log(httpOptions.headers.get("authorization"));

    this.saveToken({"access_token":"sdvksdvksdbvksdbvkbsd","expires_in":12345665,});
  }


  saveToken(token){
    var expireDate = new Date().getTime() + (1000 * token.expires_in);
    Cookie.set("access_token", token.access_token, expireDate);
    console.log('Obtained Access token');
    this._router.navigate(['/']);
  }

  getResource(srcDest) : Observable<Foo>{
    console.log('inside getResource');
    return this._http.get("http://localhost:8080/fares/"+srcDest.src+"/"+srcDest.dest)
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }

  getApiInfo():Observable<ApiStatusSummery[]>{
    return this._http.get("http://localhost:8080/metric")
      .catch((error:any) => Observable.throw(error.json().error || 'Server error'));
  }



  checkCredentials(){
    if (!Cookie.check('access_token')){
        this._router.navigate(['/login']);
    }
  }

  logout() {
    Cookie.delete('access_token');
    this._router.navigate(['/login']);
  }
}
