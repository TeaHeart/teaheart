package org.example;

public @interface Mediator {
    abstract class Person {
        protected final String name;
        protected final HouseMediator mediator;

        public Person(String name, HouseMediator mediator) {
            this.name = name;
            this.mediator = mediator;
        }

        public void contact(String msg) {
            mediator.contact(msg, this);
        }

        public void getMsg(String msg) {
            System.out.printf("%s.getMsg::%s:%s%n", getClass().getSimpleName(), name, msg);
        }
    }

    class Tenant extends Person {
        public Tenant(String name, HouseMediator mediator) {
            super(name, mediator);
        }
    }

    class HouseOwner extends Person {
        public HouseOwner(String name, HouseMediator mediator) {
            super(name, mediator);
        }
    }

    interface HouseMediator {
        void contact(String msg, Person person);
    }

    class HouseMediatorStructure implements HouseMediator {
        private HouseOwner owner;
        private Tenant tenant;

        public void setOwner(HouseOwner owner) {
            this.owner = owner;
        }

        public void setTenant(Tenant tenant) {
            this.tenant = tenant;
        }

        @Override
        public void contact(String msg, Person person) {
            if (person == owner) {
                tenant.getMsg(msg);
            } else if (person == tenant) {
                owner.getMsg(msg);
            }
        }
    }
}
