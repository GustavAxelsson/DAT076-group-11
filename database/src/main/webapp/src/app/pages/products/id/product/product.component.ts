import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {take} from "rxjs/operators";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  constructor(private route: ActivatedRoute) {
    this.route.paramMap.pipe(take(1))
      .subscribe();

  }

  ngOnInit(): void {
  }

}
