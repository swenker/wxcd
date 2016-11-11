/**
   Manually created
   A component is a basic build blocks of Angular applications and it controls a portion of the screen-a view-through
    its associated template

*/


import  { Component,OnInit } from '@angular/core';

import  { Hero }  from './hero'
import  { HeroService }  from './hero.service'


@Component({
  /* a selector that specifies a simple CCS selector for an HTML element that represents the component */
  selector: 'my-app',

  /* tell angular how to render the component's view */
  template: `
            <h1>{{title}}</h1><hr/>
            <h2>My Heroes </h2>
            <ul class="heroes">
              <li *ngFor="let hero of heroes" (click) = "onSelect(hero)" >
                <span class="badge"> {{hero.id}}</span> {{hero.name}}
              </li>
            </ul>
            <my-hero-detail [hero]="selectedHero"></my-hero-detail>

            `,
  styles: [`
    .selected {
      background-color: #CFD8DC !important;
      color: white;
    }
    .heroes {
      margin: 0 0 2em 0;
      list-style-type: none;
      padding: 0;
      width: 15em;
    }
    .heroes li {
      cursor: pointer;
      position: relative;
      left: 0;
      background-color: #EEE;
      margin: .5em;
      padding: .3em 0;
      height: 1.6em;
      border-radius: 4px;
    }
    .heroes li.selected:hover {
      background-color: #BBD8DC !important;
      color: white;
    }
    .heroes li:hover {
      color: #607D8B;
      background-color: #DDD;
      left: .1em;
    }
    .heroes .text {
      position: relative;
      top: -3px;
    }
    .heroes .badge {
      display: inline-block;
      font-size: small;
      color: white;
      padding: 0.8em 0.7em 0 0.7em;
      background-color: #607D8B;
      line-height: 1em;
      position: relative;
      left: -1px;
      top: -4px;
      height: 1.8em;
      margin-right: .8em;
      border-radius: 4px 0 0 4px;
    }
  `],

  providers:[HeroService]

})

/*
Component class controls the appearance and behavior of a view through it's template.
Our Business logic goes here ,such http request and so on.
*/
export class AppComponent implements OnInit {
  title = "Tour of Heroes";
  heroes: Hero[];
  selectedHero: Hero;

  onSelect(hero: Hero): void{
    this.selectedHero = hero;
  }

  constructor(private heroService:HeroService){ }

  getHeroes():void{
    /**
       param => statements;
       Promise's then method
     */
    this.heroService.getHeroes().then(heroes => this.heroes = heroes);

    /*this.heroes = this.heroService.getHeroes();*/

  }

  /**This is the hook method*/
  ngOnInit():void{
    this.getHeroes();
  }

}



