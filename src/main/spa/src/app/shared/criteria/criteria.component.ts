import { AfterViewInit, Component, ElementRef, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';

@Component({
  selector: 'app-criteria',
  templateUrl: './criteria.component.html',
  styleUrls: ['./criteria.component.scss']
})
export class CriteriaComponent implements OnInit, AfterViewInit {
  @Output()
  valueChange: EventEmitter<string> = new EventEmitter<string>();

  @ViewChild('filterElement')
  filterElementRef!: ElementRef;

  private _listFilter!: string;

  get listFilter() : string {
    return this._listFilter;
  }
  /**
   * Permet de transmettre la saisie au composant parent
   */
  set listFilter(value: string){
    this._listFilter = value;
    this.valueChange.emit(value);
  }

  constructor() { }

  /** permet de faire le focus sur la barre de recherche au chargement */
  ngAfterViewInit(): void {
    if (this.filterElementRef) {
      this.filterElementRef.nativeElement.focus();
    }
  }

  ngOnInit(): void {
  }

}
