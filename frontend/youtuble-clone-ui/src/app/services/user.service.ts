import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../user-dto';
import { UserDisplay } from '../user-display';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  usersUrl = 'http://localhost:8080/api/user';
  userLogged: UserDTO | undefined;
  constructor(private httpClient: HttpClient) {}

  retrieveUser(userId: string): Observable<UserDisplay> {
    return this.httpClient.get<UserDisplay>(this.usersUrl + '/' + userId);
  }

  registerUser(): void {
    this.httpClient.post<UserDTO>(this.usersUrl + '/register', {}).subscribe((response) => {
      this.userLogged = response;
      console.log('user registered: ', this.userLogged);
    });
  }

  subscribeUser(userId: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.usersUrl + '/subscribe/' + userId);
  }

  unsubscribeUser(userId: string): Observable<boolean> {
    return this.httpClient.get<boolean>(this.usersUrl + '/unsubscribe/' + userId);
  }
}
