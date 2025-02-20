import{a as A,b as B}from"./chunk-4EZRYZR3.js";import{$ as D,W as F,Z as R,_ as j,ba as k}from"./chunk-NTQFL2KH.js";import{a as q}from"./chunk-WQNLUBG3.js";import{B as E,C as I,D as V,E as N,F as W,G as L,J as O,L as T,e as P,h as w,r as b,t as y,y as S}from"./chunk-6LSA2XQ7.js";import{Cb as h,Hb as v,Jb as u,Sa as l,Tb as a,Wb as f,Xa as s,Xb as g,Yb as _,cb as C,hb as M,na as m,oa as p,ob as x,wb as i,xb as r}from"./chunk-TRC53F5N.js";function z(c,o){c&1&&(i(0,"div"),a(1,"Chargement..."),r())}function H(c,o){if(c&1){let t=h();i(0,"div")(1,"h2"),a(2,"Modifier le produit"),r(),i(3,"form",1),v("ngSubmit",function(){m(t);let e=u();return p(e.saveProduct())}),i(4,"div",2)(5,"mat-form-field",3)(6,"mat-label"),a(7,"Nom"),r(),i(8,"input",4),_("ngModelChange",function(e){m(t);let d=u();return g(d.product.name,e)||(d.product.name=e),p(e)}),r()()(),i(9,"div",2)(10,"mat-form-field",5)(11,"mat-label"),a(12,"Prix"),r(),i(13,"input",6),_("ngModelChange",function(e){m(t);let d=u();return g(d.product.price,e)||(d.product.price=e),p(e)}),r()()(),i(14,"div",2)(15,"mat-form-field",3)(16,"mat-label"),a(17,"Description"),r(),i(18,"textarea",7),_("ngModelChange",function(e){m(t);let d=u();return g(d.product.description,e)||(d.product.description=e),p(e)}),r()()(),i(19,"div",8)(20,"button",9),a(21,"Sauvegarder"),r(),i(22,"button",10),v("click",function(){m(t);let e=u();return p(e.router.navigate(["/products"]))}),a(23,"Annuler"),r()()()()}if(c&2){let t=u();l(8),f("ngModel",t.product.name),l(5),f("ngModel",t.product.price),l(5),f("ngModel",t.product.description)}}var G=class c{constructor(o,t,n){this.route=o;this.router=t;this.productService=n}product;isLoading=!0;ngOnInit(){let o=Number(this.route.snapshot.paramMap.get("id"));o?this.loadProduct(o):(console.error("ID du produit non trouv\xE9."),this.router.navigate(["/products"]))}loadProduct(o){this.productService.getProductById(o).subscribe({next:t=>{this.product=t,this.isLoading=!1},error:t=>{console.error("Erreur lors du chargement du produit :",t),this.router.navigate(["/products"])}})}saveProduct(){this.productService.updateProduct(this.product.id,this.product).subscribe({next:()=>{console.log("Produit mis \xE0 jour avec succ\xE8s."),this.router.navigate(["/products"])},error:o=>{console.error("Erreur lors de la mise \xE0 jour du produit :",o)}})}static \u0275fac=function(t){return new(t||c)(s(b),s(y),s(q))};static \u0275cmp=C({type:c,selectors:[["app-edit-product"]],decls:2,vars:2,consts:[[4,"ngIf"],[1,"form-flex-container",3,"ngSubmit"],[1,"form-row"],["appearance","fill",1,"full-width"],["matInput","","name","name","required","",3,"ngModelChange","ngModel"],["appearance","fill",1,"half-width"],["matInput","","type","number","name","price","required","",3,"ngModelChange","ngModel"],["matInput","","name","description",3,"ngModelChange","ngModel"],[1,"button-container"],["mat-button","","color","primary","type","submit"],["mat-button","","color","warn","type","button",3,"click"]],template:function(t,n){t&1&&M(0,z,2,0,"div",0)(1,H,24,3,"div",0),t&2&&(x("ngIf",n.isLoading),l(),x("ngIf",!n.isLoading))},dependencies:[w,P,T,W,S,L,E,I,O,N,V,j,R,F,B,A,k,D],styles:[".form-flex-container[_ngcontent-%COMP%]{display:flex;flex-direction:column;gap:16px;max-width:600px;margin:auto}.form-row[_ngcontent-%COMP%]{display:flex;flex-wrap:wrap;gap:16px}.full-width[_ngcontent-%COMP%]{flex:1 1 100%}.half-width[_ngcontent-%COMP%]{flex:1 1 calc(50% - 16px)}.button-container[_ngcontent-%COMP%]{display:flex;justify-content:flex-end;gap:8px}"]})};export{G as EditProductComponent};
