

export interface UserInfo{
  username?: string,
}

export interface UsersForManagement {
  id: number,
  oid: string,
  username: string,
  firstname: string,
  lastname: string,
  email: string,
  roleIds: Number[],
}

export interface UsersForManagementWithRoleNames extends UsersForManagement {
  roleNames: string[],
}

export interface MyId {
  id: Number;
}


export interface MyLanguage {
  languageName: string,
  languageLevel: string,
}
