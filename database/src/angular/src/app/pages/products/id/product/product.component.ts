import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { switchMap, take } from 'rxjs/operators';
import { ProductService } from '../../../../services/product-service/product.service';
import { of } from 'rxjs';
import { Product } from '../../../../models/product';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css'],
})
export class ProductComponent implements OnInit {
  product: Product | undefined = undefined;
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
          return of(undefined);
        })
        // switchMap(id => productService.getProductById(id))
      )
      .subscribe((product) => (this.product = product));
  }

  ngOnInit(): void {}
}
