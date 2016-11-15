/**
   Manually created
   A component is a basic build blocks of Angular applications and it controls a portion of the screen-a view-through
    its associated template

*/


import  { Component } from '@angular/core';

@Component({
  moduleId: module.id,
  /* a selector that specifies a simple CCS selector for an HTML element that represents the component */
  selector: 'my-app',

  /* tell angular how to render the component's view */
  /**template:*/
  templateUrl: 'app.component.html'
})

/*Component class controls the appearance and behavior of a view through it's template. */
export class AppComponent {
  title = "Reporting System" ;
}
