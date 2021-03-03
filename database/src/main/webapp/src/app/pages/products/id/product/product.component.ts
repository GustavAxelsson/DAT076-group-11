import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap, take } from 'rxjs/operators';
import { ProductService } from '../../../../services/product.service';
import { of, throwError } from 'rxjs';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private productService: ProductService
  ) {
    this.route.paramMap
      .pipe(
        take(1),
        switchMap((parm) => {
          const id = Number(parm.get('id'));
          if (id != undefined) {
            return productService.getProductById(id);
          }
          return of();
        })
        // switchMap(id => productService.getProductById(id))
      )
      .subscribe((res) => console.log(res));
  }

  ngOnInit(): void {}
}
