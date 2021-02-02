import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  public aboutCards: AboutCard[] = [];

  public headerOne = 'This is us!';
  public descriptionFounding = 'Our company was founded in 2021 on the backs of two hard working men';
  public descriptionWhatWeDo = 'We sell lots of stuff!';

  constructor() { }

  ngOnInit(): void {
    this.aboutCards = [
      {
        name: 'Gustav Axelsson',
        title: 'Angular Noob',
        description: 'Does very importent stuff in the company',
        email: 'gusaxe@student.chalmers.se',
        url: 'assets/peasant.png'
      },
      {
        name: 'Linus Johansson',
        title: 'Angular Pro',
        description: 'Does super importent stuff in the company',
        email: 'linjoh@student.chalmers.se',
        url: 'assets/kingen.png'
      },
    ];
  }
}

export interface AboutCard {
  name: string;
  title: string;
  description: string;
  email: string;
  url: string;
}
