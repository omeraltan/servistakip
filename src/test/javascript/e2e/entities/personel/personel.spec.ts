import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import PersonelComponentsPage from './personel.page-object';
import PersonelUpdatePage from './personel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Personel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let personelComponentsPage: PersonelComponentsPage;
  let personelUpdatePage: PersonelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    personelComponentsPage = new PersonelComponentsPage();
    personelComponentsPage = await personelComponentsPage.goToPage(navBarPage);
  });

  it('should load Personels', async () => {
    expect(await personelComponentsPage.title.getText()).to.match(/Personels/);
    expect(await personelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Personels', async () => {
    const beforeRecordsCount = (await isVisible(personelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(personelComponentsPage.table);
    personelUpdatePage = await personelComponentsPage.goToCreatePersonel();
    await personelUpdatePage.enterData();
    expect(await isVisible(personelUpdatePage.saveButton)).to.be.false;

    expect(await personelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(personelComponentsPage.table);
    await waitUntilCount(personelComponentsPage.records, beforeRecordsCount + 1);
    expect(await personelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await personelComponentsPage.deletePersonel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(personelComponentsPage.records, beforeRecordsCount);
      expect(await personelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(personelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
