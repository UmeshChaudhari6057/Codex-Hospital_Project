import { Component, EventEmitter, Input, Output } from '@angular/core';

export interface NavigationSection {
  id: string;
  label: string;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent {
  @Input({ required: true }) sections: NavigationSection[] = [];
  @Input({ required: true }) activeSection = 'overview';
  @Output() sectionSelected = new EventEmitter<string>();

  select(sectionId: string) {
    this.sectionSelected.emit(sectionId);
  }
}
