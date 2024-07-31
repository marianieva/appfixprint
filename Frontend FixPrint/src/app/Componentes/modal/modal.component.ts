import { Component, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.css']
})
export class ModalComponent {

  @Input() showModal: boolean = false;
  @Input() modalType: 'profile' | 'password' | 'new-user' | 'update-user' | 'new-print' = 'profile';
  @Output() closeModal = new EventEmitter<void>();

  close() {
    this.closeModal.emit();
  }

}
