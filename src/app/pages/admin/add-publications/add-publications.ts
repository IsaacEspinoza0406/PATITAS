import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import {inject } from '@angular/core';

@Component({
  selector: 'app-add-publications',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './add-publications.html',
  styleUrl: './add-publications.css'
})
export class AddPublications  {
}


