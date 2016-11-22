
import { Component,OnInit } from "@angular/core";


/*  */
@Component({
  /** This enable module-relative loading of templateUrl*/
  moduleId: module.id,
  selector: 'my-menu',
  templateUrl : 'menu.component.html'
})


export class MenuComponent implements OnInit{
    monthList:string[]=[];

    ngOnInit():void {
        this.monthList = [
            '2016-10','2016-11'
        ];

    }

}