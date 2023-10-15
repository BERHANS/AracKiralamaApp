package com.berhan.utility;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyFactoryRepository<T,ID> implements ICrud<T,ID>{
    private Session session;
    private Transaction transaction;
    private CriteriaBuilder criteriaBuilder;
    private EntityManager entityManager;
    T t;

    public MyFactoryRepository(T entity) {
        entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
                this.t=entity;
    }

    private void openSession(){
        session = HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();

    }
    private void closeSession(){
        transaction.commit();
        session.close();
    }


    @Override
    public T save(T entity) {
       openSession();
       session.save(entity);
       closeSession();
        return entity;
    }
    public T update(T entity) {
        openSession();
        session.update(entity);
        closeSession();
        return entity;
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
       openSession();
       entities.forEach(t->{
           session.save(t);
       });
        closeSession();
        return entities;
    }


    @Override
    public void delete(T entity) {
        openSession();
        session.delete(entity);
        closeSession();
    }

    @Override
    public void deleteById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"),id));
        T result = (T) entityManager.createQuery(criteria).getResultList();
        openSession();
        session.delete(result);
        closeSession();
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * select * from tbl.. where id=?
     * @param id
     * @return
     */
    @Override
    public Optional<T> findById(ID id) {
       CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
       Root<T> root = (Root<T>) criteria.from(t.getClass());
       criteria.select(root);
       criteria.where(criteriaBuilder.equal(root.get("id"),id));
       List<T> result = entityManager.createQuery(criteria).getResultList();
        T result1 = entityManager.createQuery(criteria).getSingleResult();
      return Optional.ofNullable(result1);
//       if(result.isEmpty()) {//eger sorgu neticesinde hiç bir deger dönmez ise boş olarak Optional dön
//          return Optional.empty();
//      }else
//          return Optional.of(result.get(0));//Eger en az bir deger dönecektri. lk dönen degeri Optional olarak dön
      //BÖYLE BİR KULLANIM DA MEVCUTTUR

    }

    @Override
    public boolean existById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"),id));
        T result = entityManager.createQuery(criteria).getSingleResult();
        return result!=null;//result null değilse true dön null ise false dön


    }

    /**
     * Burda yapılmak istenilen detay şudur. Bir sınıf içindeki alan adları(değişkenler) yazılım tarafından okunulacak
     * ve bu değişkenlerin değerleri kontrol edilerek null olmayanlar sorguya dahi edilecek. böylece esnek sorgulama
     * işlemi otomatikleştirilmiş olacak.
     * Bu işlemi yapabilmek için Java Reflection Api kullanılacaktır. Bu işlemin adına reverse engineering - Tersine
     * mühendislik olarak adlandırılabilir.
     * Musteri2 musteri2 = new Musteri2();
     *      *
     *      *         musteri2.setAd("berhan");
     *      *          musteri2.setAdSoyad("Ogrenci");
     *      * select * from tblmusteri where ad like '%M%' and soyad like '%HOCA%'
     * @param entity
     * @return
     */
    @Override
    public List<T> findByEntity(T entity) {
        List<T> result = null;
        Class cl = entity.getClass();//entitynin class özelliklerini seçiyorum
        Field[] fl = cl.getDeclaredFields();//class içindeki tüm değişkenleri bir liste içine alıyorum id, ad, soyad, v.s.
        try{
            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T> root = (Root<T>) criteria.from(t.getClass());
            criteria.select(root);
            List<Predicate> predicateList = new ArrayList<>();//sorgu için gerekli kriterlerin listesini ekleyeceğimiz liste
            for(int i=0; i<fl.length ;i++){
                fl[i].setAccessible(true);//önce erişime açtık bunu unutursak null gelicektir
                /**
                 * okumakta olduğum alan null değil ise,
                 * ayrıca okuduğum alan id değil ise.
                 */
                if(fl[i].get(entity)!=null && !fl[i].get(entity).equals("id")){
                    /**
                     * Sorguları yazarken değişkenlerin tipi önemlidir. mesela int bir değer için like kullanamazsınız
                     */
                    if(fl[i].getType().isAssignableFrom(String.class))
                       predicateList.add(criteriaBuilder.like(root.get(fl[i].getName()),"%"+fl[i].get(entity)+"%"));
                    else if(fl[i].getType().isAssignableFrom(Number.class))
                        criteriaBuilder.equal(root.get(fl[i].getName()),fl[i].get(entity));
                    else
                        criteriaBuilder.equal(root.get(fl[i].getName()),fl[i].get(entity));
                }
            }

            criteria.where(predicateList.toArray(new Predicate[]{}));//{34,5656,8989,85,6,6}
            result = entityManager.createQuery(criteria).getResultList();
        }catch (Exception e){
            System.out.println("Beklenmeyen bir hata oluştu" + e.toString());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * select* from tbl... Where column = value
     *
     * @param columnName
     * @param value-> % ve _ karakterlerini kullanıcılar tarafından girilir
     * @return
     */
    @Override
    public List<T> findByColumnNameAndValue(String columnName, String value) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get(columnName),value));//kullanıcıdan girdiği kolon adı ve degerin esitliği kontrol edilir
     //   criteria.where(criteriaBuilder.like(root.get(columnName),value));

        return entityManager.createQuery(criteria).getResultList();
    }


}
