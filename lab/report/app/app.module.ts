/** Manually created */

import { NgModule } from '@angular/core';

import { RouterModule } from '@angular/router';

import { BrowserModule } from '@angular/platform-browser';

import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { MenuComponent } from './menu.component';
import { AllUniqueComponent } from './all-unique.component';

@NgModule({
  imports:[
    BrowserModule,
    FormsModule,
    RouterModule.forRoot([
      {
        path: "menu",
        component: MenuComponent
      },
      {
        path: "auplot",
        component: AllUniqueComponent
      }
    ])
  ],

  declarations: [
    AppComponent,
    MenuComponent,
    AllUniqueComponent
   ],

  bootstrap:    [ AppComponent ]
})

export class AppModule { }

