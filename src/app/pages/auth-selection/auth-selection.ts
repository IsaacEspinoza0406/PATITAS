import { Component } from '@angular/core';
import { RouterLink } from '@angular/router'; 

@Component({
  selector: 'app-auth-selection',
  standalone: true,
  imports: [RouterLink], 
  templateUrl: './auth-selection.html',
  styleUrls: ['./auth-selection.css']
})
export class AuthSelectionComponent {
}