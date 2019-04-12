import {Component} from '@angular/core';
import {ApiInfo, ApiStatusSummery, AppService, Foo, Location} from './app.service'

@Component({
  selector: 'foo-details',
  providers: [AppService],  
  template: `<div class="container">
    <div>
      <h1 class="col-sm-12">Fare Details</h1>
      <div class="col-sm-6">
        <div class="col-sm-3 form-group">
          <label>Origin</label>
          <input class="form-control" required="true" type="text" [(ngModel)]="srcDest.src"/>
        </div>
        <div class="col-sm-3 form-group">
          <label>Destination</label>
          <input class="form-control"  required="true" type="text" [(ngModel)]="srcDest.dest"/>
        </div>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">Amount</label> <span>{{foo.amount}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">Currency</label> <span>{{foo.currency}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">Origin description</label> <span>{{foo.originLocation.description}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">Origin</label> <span>{{foo.origin}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">Destination</label> <span>{{foo.destination}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">Destination Description</label> <span>{{foo.destinationLocation.description}}</span>
      </div>
      <div class="col-sm-12">
        <button class="btn btn-primary" (click)="getFoo()" type="submit">New Foo</button>
      </div>  
    </div>
    <div>
      <h1 class="col-sm-12">Fare api Details</h1>
      <div class="col-sm-12">
        <label class="col-sm-3">Path</label> <span>{{apiInformation.path}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">200</label> <span>{{apiInformation.count200}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">400</label> <span>{{apiInformation.count400}}</span>
      </div>
      <div class="col-sm-12">
        <label class="col-sm-3">500</label> <span>{{apiInformation.count500}}</span>
      </div>
      <div class="col-sm-12">
        <button class="btn btn-primary" (click)="processApiInfo()" type="submit">New Status</button>
      </div>
      <h3>Below are the other metrics url for more statstics</h3>
      <a href="http://localhost:8080/api/metric">http://localhost:8080/api/metric</a>
      <a href="http://localhost:8080/api/metric-graph">http://localhost:8080/api/metric-graph</a>
      <a href="http://localhost:8080/api/status-metric">http://localhost:8080/api/status-metric</a>
    </div>
</div>`
})

export class FooComponent {

    public srcDest = {src:"BBA",dest:"YOW"};
    public location = new Location("","","",null, null, null);
    public foo = new Foo(0,"EUR","YOW","BBA",location,location);
    public apiInformation = new ApiInfo("",0,0,0);
    public apiStatusSummery:Array<ApiStatusSummery> = [new ApiStatusSummery("","",0,0)];
    constructor(private _service:AppService) {
      console.log("Inside foo constructor");
      this.getFoo();
      this.processApiInfo();
    }
    getFoo(){
        this._service.getResource(this.srcDest)
         .subscribe(data => this.foo = data,
                     error =>  this.foo.currency = 'Error');
    }
  processApiInfo(){
    this._service.getApiInfo().subscribe(
      data=>{
        this.apiStatusSummery = data;
        this.apiInformation.path = "/fares/*";
        var information = this.apiStatusSummery.filter(value => {
          return value.status == 200;
        });

        this.apiInformation.count200 = information && information.length != 0 ? information[0].count : 0;
        information = this.apiStatusSummery.filter(value => {
          return value.status == 500;
        });

        this.apiInformation.count500 = information && information.length != 0 ? information[0].count : 0;
        information = this.apiStatusSummery.filter(value => {
          return value.status == 400;
        });

        this.apiInformation.count400 = information && information.length != 0 ? information[0].count : 0;


      },
      error =>alert("error occured")
    )
  }
}
