package io.vigg.skmacros

import io.getquill.CassandraSyncContext
import scala.reflect.macros.whitebox.{Context => MacroContext}

trait RepoQueries {

  def getAll[T]: List[T] =
    macro RepoMacro.getAll[T]

  def get[T](id: Long): List[T] =
    macro RepoMacro.get[T]

  def delete[T](id: Long): List[T] =
    macro RepoMacro.get[T]

  def getWithFilter[T](filter: (T) => Boolean): List[T] =
    macro RepoMacro.getWithFilter[T]

  def getWithFilterAllowFiltering[T](
      filter: (T) => Boolean
  ): List[T] =
    macro RepoMacro.getWithFilterAllowFiltering[T]

  def create[T](entity: T): Unit =
    macro RepoMacro.create[T]

  def getFilteredListId[A, T](idList: List[A]): List[T] =
    macro RepoMacro.getFilteredListId[A, T]
}

class RepoMacro(val c: MacroContext) {

  import c.universe._

  def getAll[T](implicit
      t: WeakTypeTag[T]
  ): Tree =
    q"""
      import ${c.prefix}._
      run(quote {
            query[$t]
       })
    """

  def get[T](id: Tree)(implicit
      t: WeakTypeTag[T]
  ): Tree =
    q"""
      import ${c.prefix}._
      run(quote {
            query[$t].filter(_.id==lift($id))
       })
    """

  def delete[T](id: Tree)(implicit
      t: WeakTypeTag[T]
  ): Tree =
    q"""
      import ${c.prefix}._
      run(quote {
            query[$t].filter(_.id==lift($id)).delete
       })
    """

  def getWithFilter[T](filter: Tree)(implicit
      t: WeakTypeTag[T]
  ): Tree =
    q"""
      import ${c.prefix}._
      run(quote {
            query[$t].filter($filter)
       })
     """

  def getWithFilterAllowFiltering[T](filter: Tree)(implicit
      t: WeakTypeTag[T]
  ): Tree =
    q"""
      import ${c.prefix}._
      run(quote {
            query[$t].filter($filter).allowFiltering
       })
    """

  def create[T](entity: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      run(quote {
            query[$t].insertValue(lift($entity))
       })
     """

  def getFilteredListId[A, T](idList: Tree)(implicit
      t: WeakTypeTag[T],
      a: WeakTypeTag[A]
  ): Tree =
    q"""
      import ${c.prefix}._
      ${idList}.flatMap(id => {
        getWithFilterAllowFiltering[$t](entity => {
          entity.id == lift(id)
        })
      })
    """
}
