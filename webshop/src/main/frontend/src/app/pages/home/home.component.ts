import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
declare var $: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, AfterViewInit {

  @ViewChild('carousel') carousel: any


  constructor() { }

  ngOnInit(): void {

  }

  nextSlide() {
    $('.carousel').carousel('next');
  }

  previousSlide() {
    $('.carousel').carousel('prev')
  }

  ngAfterViewInit() {
  }


}
