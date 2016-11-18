/** Manually created */

import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';

import { BrowserModule } from '@angular/platform-browser';

import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { MenuComponent } from './menu.component';
import { BarChartComponent } from "./bar-chart.component";
import { DeviceCounterComponent} from "./device-counter.component";


@NgModule({
  imports:[
    BrowserModule,
    FormsModule/*,
    RouterModule.forRoot([
      {
        path: "menu",
        component: MenuComponent
      }
    ])*/
  ],

  declarations: [
    AppComponent,
    MenuComponent,
    BarChartComponent,
    DeviceCounterComponent
   ],

  bootstrap:    [ AppComponent ]
})

export class AppModule { }

