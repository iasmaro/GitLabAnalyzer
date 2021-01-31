db.createUser({
  user: 'minh',
  pwd: 'minhpassword',
  roles: [
    {
      role: 'readWrite',
      db: 'minhdb'
    }
  ]
})

