export type UserType = 'USER' | 'ADMIN' | undefined;

export interface AuthUser {
  userType: UserType | undefined;
  userId: string | undefined;
  username: string | undefined;
}
