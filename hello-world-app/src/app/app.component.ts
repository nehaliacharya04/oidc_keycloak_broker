import { Component } from '@angular/core';
import { ApiService } from './api.service';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  message: any;

  constructor(
    private apiService: ApiService,
    private keycloak: KeycloakService
  ) {
    this.apiService.getMessage().subscribe(
      (data) => {
        this.message = data;
      },
      (err) => {
        console.log('error: ', err);
      }
    );
  }

  logout() {
    this.keycloak.logout();
  }
}
